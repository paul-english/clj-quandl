(ns clj-quandl.core-test
  (:use clojure.test
        clj-quandl.core
        incanter.charts)
  (:require [incanter.core :as incanter]
            [clj-time.core :as time]
            [clj-time.format :as format]))

(deftest a-test
  (testing "Can contact the Quandl API"
    (is (= (quandl "OFDP/FUTURE_CL1"
                   :start-date "2013-04-01"
                   :end-date "2013-04-02"
                   :transform "normalize")
           '(["Date" "Open" "High" "Low" "Settle" "Volume" "Open Interest"]
               ["2013-04-02" "99.63023829087922" "99.6319018404908" "99.98957464553794" "100.12362212836099" "109.25596402033632" "97.09162889049526"]
                 ["2013-04-01" "100.0" "100.0" "100.0" "100.0" "100.0" "100.0"])))
    (is (= (quandl "OFDP/FUTURE_CL1"
                  :start-date "2013-04-01"
                  :end-date "2013-04-02"
                  :transform "normalize")
           [{:open-interest 212856.0, :volume 234613.0, :settle 94.2, :low 92.86, :high 94.48, :open 93.49, :date "2013-04-09"}
            {:open-interest 235566.0, :volume 229200.0, :settle 93.36, :low 92.46, :high 93.75, :open 93.02, :date "2013-04-08"}
            {:open-interest 256458.0, :volume 287614.0, :settle 92.7, :low 91.91, :high 93.57, :open 93.36, :date "2013-04-05"}
            {:open-interest 263467.0, :volume 367119.0, :settle 93.26, :low 92.12, :high 94.84, :open 94.5, :date "2013-04-04"}
            {:open-interest 280499.0, :volume 312019.0, :settle 94.45, :low 94.18, :high 96.96, :open 96.93, :date "2013-04-03"}]))))
