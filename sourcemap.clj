#!/usr/bin/env bash

#! top-of-file comments can be written using more #! lines, which
#! is a valid comment in both clojure and bash

":";# alternately this works too

#! The construction below uses cross-language syntactic hackery to
#! specify the -Sdeps arg in a part of the file that's interpreted
#! by clojure as clojure syntax (i.e., not a line comment), so it
#! should get syntax highlighting and other editor support

":"; DEPS=\
'{:deps
  {org.clojure/clojurescript {:mvn/version "1.10.764"}}}'
":"; exec clojure -Sdeps "$DEPS" "$0" "$@"

;; put useful code here
(require '[clojure.java.io :as io])
(import '[com.google.debugging.sourcemap SourceMapConsumerV3])

(defn lookup [map-name line column]
  (let [sm-consumer (SourceMapConsumerV3.)
        sm-file (io/file map-name)]
    (.parse sm-consumer (slurp sm-file))
    (when-let [mapping (.getMappingForLine sm-consumer line column)]
      {:src-file (.getOriginalFile mapping)
       :src-line (.getLineNumber mapping)
       :src-col (.getColumnPosition mapping)})))

(doseq [l (next (line-seq (java.io.BufferedReader. *in*)))]
  (let [[[_ file line col]] (re-seq #"([^\s(]+):(\d+):(\d+)" l)]
    (when line
      (prn
        (merge
          {:compiled-file file
           :compiled-line line
           :compiled-col col}
          (when (and line col)
            (lookup "functions/index.js.map"
                    (Integer/parseInt line)
                    (Integer/parseInt col))))))))
