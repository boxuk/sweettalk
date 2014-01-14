
(ns sweettalk.http-test
  (:require [sweettalk.http :refer :all]
            [sweettalk.test :refer [with-test-routes config req]]
            [midje.sweet :refer :all]))

(with-test-routes
  (facts about-proxying-requests
         (fact "responses have backreferences rewritten"
               (proxy-request config req) => (contains {:body "http://bar"}))
         (fact "responses have xml content type"
               (proxy-request config req) => (contains {:headers {"Content-Type" "text/xml;charset=UTF-8"}}))
         (fact "responses pass through status of proxy response"
               (proxy-request config req) => (contains {:status 201}))))

