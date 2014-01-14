
(ns sweettalk.test
  (:import (java.io ByteArrayInputStream))
  (:require [clj-http.fake :refer [with-fake-routes]]))

(def config {:ws-url "http://foo"
             :ws-connections 1
             :http-timeout 100})

(def req {:scheme :http
          :request-method :get
          :uri "/netsuite.xsl"
          :body (ByteArrayInputStream. (.getBytes "bazzle"))
          :headers {"host" "bar"}})

(def routes {"/concurrent" (fn [req]
                             (Thread/sleep 501)
                             {:status 200
                              :body "complete"})
             #".*" (fn [req]
                     {:status 201
                      :body "http://foo"})})

(defmacro with-test-routes [& body]
  `(with-fake-routes
     (apply hash-map (concat ~@routes))
     ~@body))

