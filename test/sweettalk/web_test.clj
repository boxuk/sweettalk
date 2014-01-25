
(ns sweettalk.web-test
  (:require [sweettalk.web :refer :all]
            [sweettalk.test :refer [with-test-routes config req]]
            [midje.sweet :refer :all]))

(def handler (make-handler config))

(def req-config (merge req {:uri "/_/config"}))

(defn timed-requests []
  (let [now (fn [] (.getTime (java.util.Date.)))
        start (now)
        req-concurrent (merge req {:uri "/concurrent"})]
    (let [one (future (handler req-concurrent))
          two (future (handler req-concurrent))]
      @one
      @two
      (- (now) start))))

(with-test-routes
  (facts about-endpoints
         (fact "config endpoint returns EDN"
               (handler req-config) => (contains {:headers {"Content-Type" "application/edn"}
                                                  :body (pr-str config)}))
         (fact "non-internal requests are proxied to suitetalk"
               (handler req) => (contains {:status 201
                                           :body "http://bar"}))))

(with-test-routes
  (facts about-concurrent-requests
         (fact "requests are limited to channel size"
               (timed-requests) => (roughly 900 1100))))

