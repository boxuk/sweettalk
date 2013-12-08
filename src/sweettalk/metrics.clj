
(ns sweettalk.metrics
  (:require [clj-statsd :as stats]))

(defn- configure! [config]
  (stats/setup
    (:statsd-host config)
    (:statsd-port config)))

;; Public
;; ------

(defn start [config]
  (configure! config))

