(ns spark.parse
  (:require
    [spark.eval :as eval]
    [spark.kws :as kws]))

(defn select-schema-kw
  [env]
  (let [selectors (kws/gem-get (into env {:param/gem-kw (:param/schema-kw env)
                                          :param/gem-kws (kws/schema-selectors-kws env)}))]
    (reduce
      (fn [result selector]
        (if (some? result)
          result
          (eval/function-eval (into env {:param/function-name (:eval/function-name selector)
                                         :param/selector      selector}))))
      nil
      selectors)))

(defn parser
  [env]
  (let [collection-schema-kw (:param/schema-kw env)
        collection-input (:param/input env)
        _ (println :collection-schema-kw collection-schema-kw)
        post-map
        (reduce
          (fn [post-map item]
            (let [item-schema-kw (select-schema-kw (into env {:param/input item}))
                  post-list (get post-map item-schema-kw [])
                  post-list (conj post-list item)]
              (println :item-schema-kw item-schema-kw)
              (println :item item)
              (assoc post-map item-schema-kw post-list)))
          {}
          collection-input)]
    {collection-schema-kw post-map}))

(defn select-equal-key
  [env]
  (let [entry-input (:param/input env)
        key-input (key entry-input)
        selector (:param/selector env)
        schema-kw (:parse/schema-kw selector)
        value (:parse/value selector)]
    (if (= key-input value)
      schema-kw
      nil)))
