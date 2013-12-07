
(ns scotam.nsproxy.config
  (:require [confo.core :refer [confo]]))

(def config (confo :nsproxy
                   :wsurl "https://webservices.sandbox.netsuite.com"
                   :connections 1
                   :port 8080))

