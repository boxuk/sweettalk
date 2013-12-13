
(ns sweettalk.web
  (:require [sweettalk.http :refer [proxy-request]]
            [sweettalk.log :refer [wrap-logging]]
            [sweettalk.metrics :refer [wrap-metrics]]
            [clojure.core.async :refer [chan take! alt!! timeout]]
            [ring.adapter.jetty :refer [run-jetty]]))

(def sixty-seconds-in-ms (* 60 1000))

(defn- offer [ch msg]
  (alt!!
   [[ch msg]] true
   (timeout sixty-seconds-in-ms) false))

(defn- res-error [message]
  {:status 500
   :body message})

(defn- make-caller [config]
  (fn [req]
    (try
      (proxy-request config req)
      (catch Exception e
        (res-error (.getMessage e))))))

(defn- make-handler [config]
  (let [queue (chan (:ws-connections config))
        caller (make-caller config)]
    (fn [req]
      (if (offer queue req)
        (let [res (caller req)]
          (take! queue (constantly nil))
          res)
        (res-error "Timeout")))))

;; Public
;; ------

(defn start [config]
  (run-jetty
    (-> (make-handler config)
      (wrap-metrics)
      (wrap-logging))
    {:port (:http-port config)}))

