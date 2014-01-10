
(ns sweettalk.metrics
  (:require [clj-statsd :refer [gauge increment setup with-timing]]
            [fisher.core :refer [general]]))

(defn- configure! [config]
  (setup
    (:statsd-host config)
    (:statsd-port config)
    :prefix "sweettalk."))

(defn- keep-alive [config]
  (future
    (let [timeout (:statsd-keepalive-timeout config)]
      (while true
        (let [mem (:memory (general))]
          (gauge :memory-total (:total mem))
          (gauge :memory-free (:free mem)))
        (Thread/sleep timeout)))))

;; Public
;; ------

(defn wrap-metrics [handler]
  (fn [req]
    (increment
      :request-count)
    (with-timing
      :request-time
      (handler req))))

(defn start [config]
  (configure! config)
  (keep-alive config))

