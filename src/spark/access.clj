(ns spark.access)

(defn gem-path
  ([gems params]
   (gem-path gems params (:access/gem-name params)))
  ([gems params gem-name]
   [gems (into params {:access/path [gem-name]})]))

(defn descriptors-path
  [gems params]
  (let [[gems local]
        (gem-path gems param)
        path (:access/path local)]
    [gems (into params {:access/path (conj path :facet/descriptors)})]))

(defn roles-path
  [gems params]
  (let [[gems local]
        (descriptors-path gems param)
        path (:access/path local)]
    [gems (into params {:access/path (conj path :descriptors/roles)})]))

