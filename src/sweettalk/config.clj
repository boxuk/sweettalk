
(ns sweettalk.config
  (:require [confo.core :refer [confo]]))

(def config (confo :st
                   ;; netsuite
                   :ws-url nil
                   :ws-connections 1
                   ;; http server
                   :http-port 8080
                   ;; logging
                   :log-level :info
                   :log-pattern "%d [%c: %l %n] %m\n"
                   :log-file "logs/access.log"
                   ;; statsd
                   :statsd-host "localhost"
                   :statsd-port 8125))

(if (not (:ws-url config))
  (throw (Exception. "You need to define ST_WS_URL")))

