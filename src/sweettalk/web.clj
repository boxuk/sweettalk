
(ns sweettalk.web
  (:require [sweettalk.http :refer [proxy-request]]
            [sweettalk.log :refer [wrap-logging]]
            [sweettalk.metrics :refer [wrap-metrics]]
            [compojure.core :refer [routes GET]]
            [compojure.route :refer [not-found]]
            [clojure.core.async :refer [chan take! alt!! timeout]]
            [ring.adapter.jetty :refer [run-jetty]]))

(def sixty-seconds-in-ms (* 60 1000))

(defn- offer [ch msg]
  (alt!!
   [[ch msg]] true
   (timeout sixty-seconds-in-ms) false))

(defn- error-response [message]
  {:status 500
   :body message})

(defn- make-caller [config]
  (fn [req]
    (try
      (proxy-request config req)
      (catch Exception e
        (error-response
          (.getMessage e))))))

(defn- proxy-handler [config]
  (let [queue (chan (:ws-connections config))
        caller (make-caller config)]
    (fn [req]
      (if (offer queue req)
        (let [res (caller req)]
          (take! queue (constantly nil))
          res)
        (error-response "Timeout")))))

(defn- config-handler [config]
  (fn [req]
    {:status 200
     :headers {"Content-Type" "application/edn"}
     :body (pr-str config)}))

(defn- make-handler [config]
  (-> (routes
        (GET "/_/config" [] (config-handler config))
        (not-found (proxy-handler config)))
      (wrap-metrics)
      (wrap-logging)))

;; Public
;; ------

(defn start [config]
  (run-jetty
    (make-handler config)
    {:port (:http-port config)}))

