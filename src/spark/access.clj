(ns spark.access)

(defn gem-path
  ([gems params]
   (gem-path gems params
             (:access/gem-name params)))
  ([gems params gem-name]
   (if (keyword? gem-name)
     [gems (into params {:access/path [gem-name]})]
     (throw (Exception. (str ":access/gem-name is not a keyword: " (pr-str gem-name)))))))

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
     (if (keyword? role-name)
       [gems (into params {:access/path path})]
       (throw (Exception. (str ":access/role-name is not a keyword: "
                               (pr-str role-name))))))))


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
     (if (keyword? request-name)
       [gems (into params {:access/path path})]
       (throw (Exception. (str ":access/request-name is not a keyword: "
                               (pr-str request-name))))))))
