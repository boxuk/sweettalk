
(ns sweettalk.core
  (:require [sweettalk.config :refer [config]]
            [sweettalk.web :as web]
            [sweettalk.log :as log]
            [sweettalk.metrics :as metrics])
  (:gen-class))

(defn- start []
  (if (not (:ws-url config))
    (throw (Exception. "You need to define ST_WS_URL")))
  (log/start config)
  (metrics/start config)
  (web/start config))

(defn -main []
  (start))

