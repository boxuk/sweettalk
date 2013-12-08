
(ns sweettalk.web
  (:require [sweettalk.http :refer [proxy-request]]
            [clojure.core.async :refer [chan <!! >!!]]
            [clojure.tools.logging :refer [info debug error]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clj-statsd :as stats]))

(defn- make-caller [config]
  (fn [req]
    (try
      (proxy-request config req)
      (catch Exception e
        (error (.getMessage e))))))

(defn- make-handler [config]
  (let [queue (chan (:ws-connections config))
        caller (make-caller config)]
    (fn [req]
      (>!! queue req)
      (let [res (caller req)]
        (<!! queue)
        res))))

(defn- wrap-logging [handler]
  (fn [req]
    (info "URI:" (:uri req))
    (debug "Request:" (pr-str req))
    (let [res (handler req)]
      (debug "Response:" (pr-str res))
      res)))

(defn- wrap-metrics [handler]
  (fn [req]
    (stats/increment
      :request-count)
    (stats/with-timing
      :request-time
      (handler req))))

;; Public
;; ------

(defn start [config]
  (run-jetty
    (-> (make-handler config)
      (wrap-metrics)
      (wrap-logging))
    {:port (:http-port config)}))

