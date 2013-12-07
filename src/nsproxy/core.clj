
(ns nsproxy.core
  (:require [nsproxy.config :refer [config]]
            [nsproxy.web :as web]))

(defn- start []
  (web/start config))

(defn -main []
  (start))

