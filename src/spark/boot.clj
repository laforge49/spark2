(ns spark.boot)

(defn create-gem
  [gems params]
  (let [gem-kw (:gem-kw params)
        ]
    gems))

(defn create-gems
  [gems params]
  (let [params (into params
                     {:gem-kw :gem/facets-schema})]
    gems))
