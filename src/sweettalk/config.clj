
(ns sweettalk.config
  (:require [confo.core :refer [confo]]))

(def config (confo :st
                   ;; netsuite
                   :ws-url "https://webservices.sandbox.netsuite.com"
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

