
(ns nsproxy.config
  (:require [confo.core :refer [confo]]))

(def config (confo :nsproxy
                   ;; netsuite
                   :ws-url "https://webservices.sandbox.netsuite.com"
                   :ws-connections 1
                   ;; http server
                   :http-port 8080
                   ;; logging
                   :log-level :info
                   :log-pattern "%d [%c: %l %n] %m\n"
                   :log-file "logs/access.log"))

