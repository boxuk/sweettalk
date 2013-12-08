
(ns nsproxy.core
  (:require [nsproxy.config :refer [config]]
            [nsproxy.web :as web]
            [nsproxy.log :as log]
            [nsproxy.metrics :as metrics]))

(defn- start []
  (log/start config)
  (metrics/start config)
  (web/start config))

(defn -main []
  (start))

