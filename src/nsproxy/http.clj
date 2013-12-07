
(ns nsproxy.http
  (:require [clj-http.client :as http]
            [clojure.tools.logging :refer [debug]]))

;; Public
;; ------

(defn proxy-request [config req]
  (let [url (format "%s%s"
                    (:ws-url config)
                    (:uri req))
        opts {}]
    (debug "Proxy:" url (pr-str opts))
    (http/get url opts)))

