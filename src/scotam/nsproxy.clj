
(ns scotam.nsproxy
  (:require [scotam.nsproxy.config :refer [config]]
            [scotam.nsproxy.web :as web]))

(defn- start []
  (web/start config))

(defn -main []
  (start))

