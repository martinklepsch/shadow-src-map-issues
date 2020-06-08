(ns icebreaker.server.util-reduced)

(defn ^:export something-that-throws [x]
  (throw (js/Error. (str "[" x "] Fabricated example exception"))))

(defn send-unauthz
  [res & [err]]
  (.. res
      (status 403)
      (set "Content-Type" "application/transit+json")
      (send (str
              {:error (str "Unauthorized"
                           (when err
                             (str ": " (.-stack err))))}))))

(defn send-transit
  [res val]
  (doto res
    (.set "Content-Type" "application/transit+json")
    (.send (str val))))

(defn https-fn
  "Turn a function into a Firebase HTTPs function handler."
  [handler-fn]
  handler-fn)

(defn wrap-handlers
  [handlers & middleware]
  (->> (for [[k f] handlers]
         [k ((apply comp middleware) f)])
       (into {})))
