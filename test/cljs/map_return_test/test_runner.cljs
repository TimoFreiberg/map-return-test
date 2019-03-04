(ns map-return-test.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [map-return-test.core-test]
   [map-return-test.common-test]))

(enable-console-print!)

(doo-tests 'map-return-test.core-test
           'map-return-test.common-test)
