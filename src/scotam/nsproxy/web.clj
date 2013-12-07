
(ns scotam.nsproxy.web
  (:require [scotam.nsproxy.http :refer [http-proxy]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.core.async :refer [chan go <!! >!!]]))

(defn make-handler [config]
  (let [queue (chan (:connections config))]
    (fn [req]
      (>!! queue req)
      (let [res (http-proxy req)]
        (<!! queue)
        res))))

;; Public
;; ------

(defn start [config]
  (run-jetty
    (make-handler config)
    {:port (:port config)}))

