(ns icebreaker.server.srcmaptest
  (:require [icebreaker.server.util-reduced :as s-util]))

(defn match
  [_ res]
  (if-not (rand-nth [true false])
    (s-util/send-unauthz res)
    (-> (js/Promise.)
        (.then #(s-util/send-transit res (count %)))
        (.catch #(s-util/send-unauthz res)))))

(def exports
  (-> {"match"  match
       "throw"  #(s-util/something-that-throws (str "api-endpoint-error"))}
      (s-util/wrap-handlers s-util/https-fn)
      (clj->js)))

(defn -main []
  ((.-throw exports)))
