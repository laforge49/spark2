(ns spark.boot
  (:require [spark.pretty :as pretty]))

(defn create-gem
  ([gems params]
   (create-gem gems params (:boot/gem-kw params)))
  ([gems params gem-kw]
   (let [gem
         {:facet/id          gem-kw
          :facet/descriptors {:descriptors/roles {:a "b"}}}
         gems
         (assoc gems gem-kw gem)]
     [gems params])))

(defn create-gems
  [gems params]
  (let [[gems local] (create-gem gems params :gem/facets-schema)
        [gems local] (pretty/debug gems local [:gem/facets-schema])]
    [gems params]))
