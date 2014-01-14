
(defproject scotam/sweettalk "0.1.0"
  :description "Proxy for pooling connections to Netsuite 'SuiteTalk' API"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [clj-http "0.7.8"]
                 [clj-logging-config "1.9.10"]
                 [org.clojure/tools.logging "0.2.6"]
                 [rodnaph/confo "0.7.0"]
                 [rodnaph/fisher "0.2.0"]
                 [clj-statsd "0.3.10"]
                 [compojure "1.1.6"]]
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [clj-http-fake "0.7.8"]]}}
  :plugins [[lein-bin "0.3.4"]
            [lein-midje "3.1.3"]]
  :bin {:name "sweettalk"}
  :jvm-opts ["-Xmx64m"]
  :main sweettalk.core)

