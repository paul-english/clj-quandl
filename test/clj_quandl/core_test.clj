(ns clj-quandl.core-test
  (:use clojure.test
        clj-quandl.core))

(deftest a-test
  (testing "Can contact the Quandl API"
    (is (= (quandl "QUANDL/USDARS"
                   :start-date "2013-04-01"
                   :end-date "2013-04-02"
                   :transform "normalize")
           {}))))

;; TODO test returning a map
;; TODO test returning a default array
;; TODO test with other params
;; TODO test basic pre assertions
