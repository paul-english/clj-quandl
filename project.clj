(defproject clj-quandl "0.1.0-SNAPSHOT"
  :description "Quandl API wrapper"
  :url "http://quandl.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [clojure-csv/clojure-csv "2.0.0-alpha1"]
                 [clj-http "0.7.0"]]
  :profiles {:dev {:dependencies [[incanter "1.4.1"]
                                  [clj-time "0.5.0"]]}})
