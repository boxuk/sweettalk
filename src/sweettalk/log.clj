
(ns sweettalk.log
  (:require [clj-logging-config.log4j :refer [set-logger!]]))

(defn start [config]
  (set-logger! "sweettalk"
               :level (:log-level config)
               :out (org.apache.log4j.RollingFileAppender.
                      (org.apache.log4j.EnhancedPatternLayout.
                        (:log-pattern config))
                      (:log-file config)
                      true)))

