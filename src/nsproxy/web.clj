
(ns nsproxy.web
  (:require [nsproxy.http :refer [proxy-request]]
            [clojure.tools.logging :refer [info debug error]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.core.async :refer [chan <!! >!!]]))

(defn- make-caller [config]
  (fn [req]
    (try
      (proxy-request config req)
      (catch Exception e
        (error (.getMessage e))))))

(defn- make-handler [config]
  (let [queue-size (:ws-connections config)
        queue (chan queue-size)
        caller (make-caller config)]
    (info "Queue size:" queue-size)
    (fn [req]
      (info "URI:" (:uri req))
      (debug (pr-str req))
      (>!! queue req)
      (let [res (caller req)]
        (<!! queue)
        res))))

;; Public
;; ------

(defn start [config]
  (run-jetty
    (make-handler config)
    {:port (:http-port config)}))

