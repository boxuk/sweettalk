
(ns nsproxy.core
  (:require [nsproxy.config :refer [config]]
            [nsproxy.web :as web]
            [nsproxy.log :as log]))

(defn- start []
  (log/start config)
  (web/start config))

(defn -main []
  (start))

