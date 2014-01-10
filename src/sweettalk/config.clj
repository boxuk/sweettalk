
(ns sweettalk.config
  (:require [confo.core :refer [confo]]))

(def sixty-seconds-in-ms (* 60 1000))

(def config (confo :st
                   ;; netsuite
                   :ws-url nil
                   :ws-connections 1
                   ;; http server
                   :http-port 8080
                   :http-timeout sixty-seconds-in-ms
                   ;; logging
                   :log-level :info
                   :log-pattern "%d [%c: %l %n] %m\n"
                   :log-file "logs/access.log"
                   ;; statsd
                   :statsd-host "localhost"
                   :statsd-port 8125
                   :statsd-keepalive-timeout sixty-seconds-in-ms))

