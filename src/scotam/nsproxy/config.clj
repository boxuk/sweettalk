
(ns scotam.nsproxy.config
  (:require [confo.core :refer [confo]]))

(def config (confo :nsproxy
                   :port 8080
                   :connections 1))

