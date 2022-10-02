(ns spark.boot
  (:require [spark.kws :as kws]
            [spark.parse :as parse]
            [spark.pretty :as pretty]))

(def gems-atom
  (atom {}))

(def initial-env
  {:env/gems-atom-kw gems-atom})

(defn create-gem
  [env]
  (let [gem-kw (:param/gem-kw env)
        gem-value
        {:facet/id          gem-kw
         :facet/descriptors {:descriptors/roles {}}}
        gem (atom gem-value)
        gems-atom (:env/gems-atom-kw env)]
    (swap! gems-atom assoc gem-kw gem)
    gem))

(defn create-role
  [env]
  (let [gem (kws/env-gem env)
        requests-kws (kws/gem-requests-kws env)]
    (swap! gem assoc-in requests-kws {})))

(defn create-request
  [env]
  (let [gem (kws/env-gem env)
        request-kw (:param/request-kw env)
        function-name (:param/function-name env)
        request-params (get env :param/request-params {})
        request (into {:eval/function-name function-name} request-params)
        requests-kws (kws/gem-requests-kws env)
        requests (get-in @gem requests-kws)
        requests (into requests {request-kw request})]
    (swap! gem assoc-in requests-kws requests)))

(defn create-selector
  [env]
  (let [schema-kw (:param/schema-kw env)
        function-name (:param/function-name env)
        value (:param/value env)
        selector {:parse/schema-kw    schema-kw
                  :eval/function-name function-name}
        selector (if (some? value)
                   (into selector {:parse/value value})
                   selector)]
    (kws/gem-update (into env {:param/gem-kws (kws/schema-selectors-kws env)
                               :param/gem-update selector}))))

(defn create-gems
  [env]
  (let [env (into env {:param/gem-kw :gem/facets-schema})
        gem (create-gem env)]
    (create-selector (into env {:param/schema-kw     :gem/facet-id-schema
                                :param/function-name "spark.parse/select-equal-key"
                                :param/value         :facet/id}))
    (create-selector (into env {:param/schema-kw     :gem/facet-descriptors-schema
                                :param/function-name "spark.parse/select-equal-key"
                                :param/value         :facet/descriptors}))
    (let [env (into env {:param/role-kw :roles/test})]
      (create-role env)
      (create-request (into env {:param/request-kw    :debug/ribbit-request
                                 :param/function-name "spark.debug/ribbit"}))
      (create-request (into env {:param/request-kw     :debug/print-value
                                 :param/function-name  "spark.debug/print-value"
                                 :param/request-params {:param/value "Sam I am"}})))
    (println @gem)
    (pretty/debug @gem)
    (println)
    (pretty/debug (parse/parser (into env {:param/schema-kw :gem/facets-schema
                                           :param/input     @gem})))))
