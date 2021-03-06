
(ns sweettalk.web
  (:require [sweettalk.http :refer [proxy-request]]
            [sweettalk.log :refer [wrap-logging]]
            [sweettalk.metrics :refer [wrap-metrics]]
            [compojure.core :refer [routes GET context]]
            [clojure.core.async :refer [chan take! alt!! timeout]]
            [ring.adapter.jetty :refer [run-jetty]]))

(defn- offer [ch msg ms]
  (alt!!
   [[ch msg]] true
   (timeout ms) false))

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
        timeout-ms (:http-timeout config)
        caller (make-caller config)]
    (fn [req]
      (if (offer queue req timeout-ms)
        (let [res (caller req)]
          (take! queue (constantly nil))
          res)
        (error-response "Timeout")))))

(defn- config-handler [config]
  (fn [req]
    {:status 200
     :headers {"Content-Type" "application/edn"}
     :body (pr-str config)}))

(defn- internal-routes [config]
  (context "/_" []
           (GET "/config" [] (config-handler config))))

;; Public
;; ------

(defn make-handler [config]
  (-> (routes
        (internal-routes config)
        (proxy-handler config))
      (wrap-metrics)
      (wrap-logging)))

(defn start [config]
  (run-jetty
    (make-handler config)
    {:port (:http-port config)}))

