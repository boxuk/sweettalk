
(ns nsproxy.http
  (:require [clj-http.client :as http]))

;; Public
;; ------

(defn proxy-request [config req]
  (let [url (format "%s%s"
                    (:wsurl config)
                    (:uri req))
        opts {}]
    (http/get url opts)))

