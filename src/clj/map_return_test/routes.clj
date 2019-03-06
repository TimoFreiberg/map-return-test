(ns map-return-test.routes
  (:require [clojure.java.io :as io]
            [compojure.core :refer [ANY GET PUT POST DELETE routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :as response :refer [response]]))

(defn home-routes [endpoint]
  (routes
   (GET "/" _
     (-> "public/index.html"
         io/resource
         io/input-stream
         response
         (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
   (GET "/test" _
     (response {:a "x"}))
   (GET "/test-with-content-type" _
     (-> (response {:a "x"})
         (response/content-type "application/json")))
   (resources "/")))

(def muuntaja-example
  (muuntaja.middleware/wrap-format (fn [_]
                                     (response {:a "x"}))))

(def muuntaja-example-with-content-type
  (muuntaja.middleware/wrap-format (fn [_]
                                     (-> (response {:a "x"})
                                         (response/content-type "application/json")))))

(def request {:headers {"accept" "application/json"}})

(comment
  ;;works as expected, body is converted to JSON
  (muuntaja-example request)

  ;;leaves the body as is - didn't expect that but makes sense?
  (muuntaja-example-with-content-type request)

  ;;body not encoded, throws exception
  ;;request is repeated (occurs in fresh chestnut project as well)
  (clojure.java.shell/sh "curl"
                         "-H"
                         "accept: application/json"
                         "localhost:10555/test")

  ;;also fails with the exact same errors
  (clojure.java.shell/sh "curl"
                         "-H"
                         "accept: application/json"
                         "localhost:10555/test-with-content-type")
  )
