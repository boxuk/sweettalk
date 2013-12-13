
(ns sweettalk.log
  (:require [clojure.tools.logging :refer [info debug error]]
            [clj-logging-config.log4j :refer [set-loggers!]])
  (:import (org.apache.log4j RollingFileAppender EnhancedPatternLayout)
           (org.apache.log4j.net SyslogAppender)))

(defn- configure! [config]
  (set-loggers!
    "sweettalk"
    {:level (:log-level config)
     :out (RollingFileAppender.
            (EnhancedPatternLayout.
              (:log-pattern config))
            (:log-file config)
            true)}
    "syslog"
    {:level (:log-level config)
     :out (SyslogAppender.)}))

;; Public
;; ------

(defn wrap-logging [handler]
  (fn [req]
    (info "URI:" (:uri req))
    (debug "Request:" (pr-str req))
    (let [res (handler req)]
      (debug "Response:" (pr-str res))
      (if (= 500 (:status res))
        (error (:body res)))
      res)))

(defn start [config]
  (configure! config))

