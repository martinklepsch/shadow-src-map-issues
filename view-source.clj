#!/usr/bin/env bb -I

(doseq [{:keys [src-file src-line]} *input*
        :let [w-src (str "src/" src-file)]]
  (println src-file src-line)
  (when (and src-file (.exists (io/file w-src)))
    (println (:out (shell/sh "fish" "-c"
                             (str "nl -ba " w-src " | "
                                  "tail -n +" src-line " | "
                                  "head | "
                                  "bat -l clojure"
                                  ))))))
