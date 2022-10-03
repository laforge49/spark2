(ns spark.boot
  (:require [spark.pretty :as pretty]))

(defn create-gem
  [gems params]
  (let [gem-kw (:gem-kw params)
        gem
        {:facet/id          gem-kw
         :facet/descriptors {:descriptors/roles {}}}
        gems
        (assoc gems gem-kw gem)
        ]
    gems))

(defn create-gems
  [gems params]
  (let [params (into params
                     {:gem-kw :gem/facets-schema})
        gems (create-gem gems params)
        gems (pretty/debug gems (into params
                                      {:path [:gem/facets-schema]}))]
    gems))
