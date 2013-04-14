# Clojure Quandl API Wrapper

Access the Quandl dataset API using Clojure

## Install

You can install this dependency from the clojars.org repository

    [clj-quandl "0.1.0-SNAPSHOT"]

## Usage

    (use 'clj-quandl.core)
    (def oil-futures (quandl "OFDP/FUTURE_CL1"))

    (take 5 oil-futures)
    ;; => ([Date Open High Low Settle Volume Open Interest] [2013-04-09 93.49 94.48 92.86 94.2 234613.0 212856.0] [2013-04-08 93.02 93.75 92.46 93.36 229200.0 235566.0] [2013-04-05 93.36 93.57 91.91 92.7 287614.0 256458.0] [2013-04-04 94.5 94.84 92.12 93.26 367119.0 263467.0])

This will return a sequence of vectors containing entries to data
fetched directly from the Quandl API. We can return a map of this data
using the `:map-data` option.

    (def oil-futures (quandl "OFDP/FUTURE_CL1" :map-data true))

    (take 5 oil-futures)
    ;; => ({:Open Interest 212856.0, :Volume 234613.0, :Settle 94.2, :Low 92.86, :High 94.48, :Open 93.49, :Date 2013-04-09} {:Open Interest 235566.0, :Volume 229200.0, :Settle 93.36, :Low 92.46, :High 93.75, :Open 93.02, :Date 2013-04-08} {:Open Interest 256458.0, :Volume 287614.0, :Settle 92.7, :Low 91.91, :High 93.57, :Open 93.36, :Date 2013-04-05} {:Open Interest 263467.0, :Volume 367119.0, :Settle 93.26, :Low 92.12, :High 94.84, :Open 94.5, :Date 2013-04-04} {:Open Interest 280499.0, :Volume 312019.0, :Settle 94.45, :Low 94.18, :High 96.96, :Open 96.93, :Date 2013-04-03})

We can quickly create a
[time-series plot](http://jakemccrary.com/blog/2010/02/21/plotting-time-series-data-with-incanter/)
using the [Incanter](http://incanter.org/) library

    (ns test
      (:use incanter.charts)
      (:require [incanter.core :as incanter]
                [clj-time.core :as time]
                [clj-time.format :as format]))

    (def oil-futures (quandl "OFDP/FUTURE_CL1"
                       :start-date "2000-04-01"
                       :end-date "2013-04-14"
                       :map-data true))
    (def oil-formatter (format/formatter "yyyy-MM-dd"))
    (def dates (doall (map (fn [future]
                             (-> (format/parse oil-formatter
                                               (:date future))
                                 (time/to-time-zone (time/time-zone-for-offset 0))
                                 .getMillis))
                           oil-futures)))
    (println "dates" dates)
    (def volumes (doall (map #(-> (:volume %)
                                  Double/parseDouble) oil-futures)))
    (println "volumes" volumes)
    (def chart (time-series-plot dates volumes
                                 :x-label "Date"
                                 :y-label "Volume"
                                 :title "NYMEX Crude Oil Futures, Continuous Contract #1 (CL1) (Front Month)"))
    (incanter/view chart :width 800 :height 400)
    (incanter/save chart "chart.png" :width 800 :height 400)

## License

Copyright © 2013

Distributed under the Eclipse Public License, the same as Clojure.
