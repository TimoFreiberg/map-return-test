(ns map-return-test.config
  (:require [environ.core :refer [env]]
            muuntaja.middleware
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]))

(defn config []
  {:http-port  (Integer. (or (env :port) 10555))
   :middleware [[wrap-defaults api-defaults]
                wrap-with-logger
                wrap-gzip
                muuntaja.middleware/wrap-format]})
