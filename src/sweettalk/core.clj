
(ns sweettalk.core
  (:require [sweettalk.config :refer [config]]
            [sweettalk.web :as web]
            [sweettalk.log :as log]
            [sweettalk.metrics :as metrics]))

(defn- start []
  (log/start config)
  (metrics/start config)
  (web/start config))

(defn -main []
  (start))

