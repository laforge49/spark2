(ns spark.access)

(defn gem-path
  ([gems params]
   (gem-path gems params
             (:access/gem-name params)))
  ([gems params gem-name]
   [gems (into params {:access/path [gem-name]})]))

(defn descriptors-path
  ([gems params]
   (descriptors-path gems params
                     (:access/gem-name params)))
  ([gems params gem-name]
   (let [[gems local]
         (gem-path gems params gem-name)
         path (:access/path local)
         path (conj path :facet/descriptors)]
     [gems (into params {:access/path path})])))

(defn roles-path
  ([gems params]
   (roles-path gems params
               (:access/gem-name params)))
  ([gems params gem-name]
   (let [[gems local]
         (descriptors-path gems params gem-name)
         path (:access/path local)
         path (conj path :descriptors/roles)]
     [gems (into params {:access/path path})])))

(defn role-path
  ([gems params]
   (role-path gems params
              (:access/gem-name params)
              (:access/role-name params)))
  ([gems params gem-name role-name]
   (let [[gems local]
         (roles-path gems params gem-name)
         path (:access/path local)
         path (conj path role-name)]
     [gems (into params {:access/path path})])))


(defn requests-path
  ([gems params]
   (requests-path gems params
                  (:access/gem-name params)
                  (:access/role-name params)))
  ([gems params gem-name role-name]
   (let [[gems local]
         (role-path gems params gem-name role-name)
         path (:access/path local)
         path (conj path :role/requests)]
     [gems (into params {:access/path path})])))


(defn request-path
  ([gems params]
   (request-path gems params
                 (:access/gem-name params)
                 (:access/role-name params)
                 (:access/request-name params)))
  ([gems params gem-name role-name request-name]
   (let [[gems local]
         (requests-path gems params gem-name role-name)
         path (:access/path local)
         path (conj path request-name)]
     [gems (into params {:access/path path})])))
