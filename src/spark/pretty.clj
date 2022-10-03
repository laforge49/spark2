(ns spark.pretty
  (:require [clojure.string :as string]))

(defn asString=
  [gems params]
  [gems [(:value params)]])

(defn debug
  [gems params]
  (let [path (:path params)
        value (get-in gems path)
        params (into params {:value value})
        [gems lines] (asString= gems params)]
    (println (string/join "\n" (into ["---"]
                                     lines)))
    gems))
