(ns clj-quandl.core
  (:require [clj-http.client :as client]
            [clojure-csv.core :as csv]))

(def quandl-api "http://www.quandl.com/api/v1/datasets")

(def allowed {:frequency ["daily" "weekly" "monthly" "quarterly" "annual"]
              :transform ["rdiff" "diff" "cumul" "normalize"]
              :sort-order ["asc" "desc"]})

(defn quandl
  ;; TODO require start-date & end-date to be datetime objects &
  ;; format them into the api request
  [dataset & {:keys [auth-token start-date end-date frequency transform rows map-data sort-order exclude-headers]}]
  {:pre [(if end-date (not (nil? start-date)) true)
         (if frequency (some #{frequency} (:frequency allowed)) true)
         (if transform (some #{transform} (:transform allowed)) true)
         (if sort-order (some #{sort-order} (:sort-order allowed)) true)]}
  (let [url (str quandl-api "/" dataset ".csv?"
                 (when auth-token (str "auth_token=" auth-token "&"))
                 (when start-date (str "trim_start=" start-date "&"))
                 (when end-date (str "trim_end=" end-date "&"))
                 (when frequency (str "collapse=" frequency "&"))
                 (when transform (str "transformation=" transform "&"))
                 (when rows (str "rows=" rows "&"))
                 (when sort-order (str "sort_order=" sort-order "&"))
                 (when exclude-headers "exclude_headers=true"))
        response (client/get url)
        data (csv/parse-csv (:body response))]
    (if map-data
      (map (partial zipmap (map keyword (first data)))
           (rest data))
      data)))

(defn put-quandl [data code name & {:keys [auth-token desc override]}]
  ;; TODO require auth-token
  ;; TODO require data to have a date attribute
  ;; TODO validate name, code
  ;; TODO write-csv for data
  (let [url (str quandl-api ".json?auth_token=" auth-token)
        params {:name name
                :code code
                :description desc
                :update_or_create override
                :data data}
        response (client/post url {:form-params params})]
    (println "response" response)))
