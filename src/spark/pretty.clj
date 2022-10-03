(ns spark.pretty
  (:require [clojure.string :as string]))

(defn blanks
  [count]
  (string/join (repeat count " ")))

(defn asString=
  [gems params]
  (let [prefix (get params :prefix "")
        unprefix (blanks (count prefix))
        value (:value params)]
    [gems [value]]))

(defn debug=
  [gems params]
  (let [path (:path params)
        value (get-in gems path)
        params (into params {:value value
                             :prefix ""})
        [gems lines] (asString= gems params)]
    [gems (string/join "\n" (into ["---"]
                                     lines))]))

(defn debug
  [gems params]
  (let [[gems txt] (debug= gems params)]
    (println txt)
    gems))
