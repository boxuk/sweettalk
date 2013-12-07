
(ns scotam.nsproxy.web
  (:require [scotam.nsproxy.http :refer [proxy-request]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.core.async :refer [chan <!! >!!]]))

(defn- make-caller [config]
  (fn [req]
    (try
      (proxy-request config req)
      (catch Exception e
        (println (.getMessage e))))))

(defn- make-handler [config]
  (let [queue (chan (:connections config))
        caller (make-caller config)]
    (fn [req]
      (>!! queue req)
      (let [res (caller req)]
        (<!! queue)
        res))))

;; Public
;; ------

(defn start [config]
  (run-jetty
    (make-handler config)
    {:port (:port config)}))

