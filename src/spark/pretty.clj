(ns spark.pretty)

(defn asString=
  [gems params]
  (:value params))

(defn debug
  [gems params]
  (let [path (:path params)
        value (get-in gems path)
        params (into params {:value value})]
    (println (asString= gems params))
    gems))
