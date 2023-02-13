(ns spark.access)

(defn gem-path
  ([gems params]
   (gem-path gems params (:access/gem-name params)))
  ([gems params gem-name]
   [gems (into params {:access/path [gem-name]})]))

(defn descriptors-path
  [gems params]
  (let [[gems local]
        (gem-path gems params)
        path (:access/path local)
        path (conj path :facet/descriptors)]
    [gems (into params {:access/path path})]))

(defn roles-path
  [gems params]
  (let [[gems local]
        (descriptors-path gems params)
        path (:access/path local)
        path (conj path :descriptors/roles)]
    [gems (into params {:access/path path})]))

(defn role-path
  ([gems params]
   (role-path gems params (:access/role-name params)))
  ([gems params role-name]
   (let [[gems local]
         (roles-path gems params)
         path (:access/path local)
         path (conj path role-name)]
     [gems (into params {:access/path path})])))
