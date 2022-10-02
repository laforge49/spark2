(ns spark.kws)

(defn env-gem
  [env]
  (get @(:env/gems-atom-kw env) (:param/gem-kw env)))

(defn gem-get
  [env]
  (get-in @(env-gem env) (:param/gem-kws env)))

(defn gem-update
  [env]
  (swap! (env-gem env)
         (fn [gem]
           (let [kws (:param/gem-kws env)]
             (assoc-in gem
                       kws
                       (conj (get-in gem kws) (:param/gem-update env)))))))

(defn gem-descriptors-kws
  [env]
  [:facet/descriptors])

(defn gem-role-kws
  [env]
  (into (gem-descriptors-kws env) [:descriptors/roles (:param/role-kw env)]))

(defn gem-requests-kws
  [env]
  (conj (gem-role-kws env) :role/requests))

(defn gem-request-kws
  [env request-kw]
  (conj (gem-requests-kws env) (:param/request-kw env)))

(defn schema-selectors-kws
  [env]
  (conj (gem-descriptors-kws env) :parse/selectors))
