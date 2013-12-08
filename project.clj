
(defproject scotam/sweettalk "0.1.0"
  :description "Proxy for pooling connections to Netsuite 'SuiteTalk' API"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [clj-http "0.7.7"]
                 [clj-logging-config "1.9.10"]
                 [org.clojure/tools.logging "0.2.6"]
                 [rodnaph/confo "0.7.0"]
                 [clj-statsd "0.3.10"]]
  :main sweettalk.core)

