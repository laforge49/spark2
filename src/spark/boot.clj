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
    [gems params]))

(defn create-gems
  [gems params]
  (let [local (into params
                     {:gem-kw :gem/facets-schema})
        [gems local] (create-gem gems local)
        [gems local] (pretty/debug gems (into local
                                      {:path [:gem/facets-schema]}))
        ]
    [gems params]))
