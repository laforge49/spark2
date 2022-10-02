(ns spark.pretty
  (:require [clojure.string :as string]))

(defn blanks
  [count]
  (string/join (repeat count " ")))

(defn asString-
  [env]
  (let [prefix (get env :param/prefix "")
        length (count prefix)
        unprefix (blanks length)
        value (:param/value env)]
    (cond
      (vector? value)
      (let [first-prefix (string/join [prefix "- "])]
        (first
          (reduce
            (fn [[lst prefix] item]
              (let [ivs (asString- (into env {:param/prefix prefix
                                              :param/value  item}))
                    lst (into lst ivs)
                    ]
                [lst (string/join [unprefix "- "])]))
            [[] first-prefix]
            value)))
      (map? value)
      (let []
        (first
          (reduce
            (fn [[lst prefix] [k v]]
              (let [env (into env {:param/prefix prefix})
                    kv (first (asString- (into env {:param/prefix prefix
                                                    :param/value  k})))
                    kpv (string/join [kv ":"])
                    lst (conj lst kpv)
                    lst (into lst (asString- (into env {:param/prefix (string/join unprefix "  ")
                                                        :param/value  v})))]
                [lst unprefix]))
            [[] prefix]
            value)))
      :else
      (let [prefix (get env :param/prefix "")
            s (pr-str value)
            j (string/join [prefix s])]
        [j]))))

(defn asString
  [env]
  (string/join "\n" (into ["---"] (asString- env))))

(defn debug
  [value]
  (println (asString {:param/value value}))
  (flush))
