
(defproject scotam/nsproxy "0.1.0-SNAPSHOT"
  :description "Proxy for pooling connections to Netsuite SuiteTalk API"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [clj-http "0.7.7"]
                 [rodnaph/confo "0.7.0"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]]
  :main nsproxy.core)

