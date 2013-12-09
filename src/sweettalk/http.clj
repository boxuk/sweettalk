
(ns sweettalk.http
  (:require [clj-http.client :as http]
            [clojure.string :as string]))

(defn- req-headers [req]
  (select-keys
    (:headers req)
    ["user-agent" "soapaction" "content-type"]))

(defn- req-method [req]
  (if (= :post (:request-method req))
                http/post
                http/get))

(defn- res-body [config req res]
  (string/replace
    (:body res)
    (re-pattern (:ws-url config))
    (format "%s://%s"
            (name (:scheme req))
            (get-in req [:headers "host"]))))

;; Public
;; ------

(defn proxy-request [config req]
  (let [url (format "%s%s"
                    (:ws-url config)
                    (:uri req))
        opts {:headers (req-headers req)
              :content-type "text/xml"
              :body (slurp (:body req))}
        res ((req-method req) url opts)]
    (merge
      (select-keys res [:status :content-type])
      {:body (res-body config req res)})))

