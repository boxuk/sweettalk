
(ns sweettalk.metrics
  (:require [clj-statsd :as stats]
            [fisher.core :refer [general]]))

(def sixty-seconds-ms (* 60 1000))

(defn- configure! [config]
  (stats/setup
    (:statsd-host config)
    (:statsd-port config)
    :prefix "sweettalk."))

(defn- keep-alive []
  (future
    (while true
      (let [mem (:memory (general))]
        (stats/gauge :memory-total (:total mem))
        (stats/gauge :memory-free (:free mem)))
      (Thread/sleep sixty-seconds-ms))))

;; Public
;; ------

(defn start [config]
  (configure! config)
  (keep-alive))

