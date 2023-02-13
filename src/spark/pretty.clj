(ns spark.pretty
  (:require [clojure.string :as string]))

(defn simpleScalar=
  ([gems params]
   (simpleScalar= gems params
                  (:pretty/value params)))
  ([gems params value]
   (let [s
         (if (string? value)
           value
           (pr-str value))]
   [gems (into params {:pretty/simple s})])))

(defn structure?
  [s]
  (or (map? s)))

(defn asString=
  ([gems params]
   (asString= gems params
              (:pretty/prefix params)
              (:pretty/value params)))
  ([gems params prefix value]
   (cond
     (structure? value)
     (let [[gems local lines]
           (reduce
             (fn [[gems local lines] [k v]]
               (if (structure? v)
                 (let [[gems local]
                       (simpleScalar= gems local k)
                       simpleKey (:pretty/simple local)
                       lines
                       (conj lines
                             (string/join [prefix simpleKey ":"]))
                       pre
                       (string/join prefix "  ")
                       [gems local]
                       (asString= gems local pre v)
                       valueLines (:pretty/lines local)
                       lines
                       (into lines valueLines)]
                   [gems local lines])
                 (let [[gems local]
                       (simpleScalar= gems local k)
                       simpleKey (:pretty/simple local)
                       [gems local]
                       (simpleScalar= gems local v)
                       simpleValue (:pretty/simple local)
                       lines
                       (conj lines
                             (string/join [prefix simpleKey ": " simpleValue]))]
                   [gems local lines])))
             [gems params []]
             value)]
       [gems (into local {:pretty/lines lines})])
     :else
     (let [[gems local]
           (simpleScalar= gems params value)
           simpleValue (:pretty/simple local)]
       [gems (into params {:pretty/lines [(string/join prefix simpleValue)]})]))))

(defn debug=
  ([gems params]
   (debug= gems params
           (:pretty/path params)))
  ([gems params path]
   (let [value (get-in gems path)
         [gems local] (asString= gems params "" value)
         txt (string/join "\n" (into ["---"] (:pretty/lines local)))]
     [gems (into params {:pretty/txt txt})])))

(defn debug
  ([gems params]
   (debug gems params
          (:pretty/path params)))
  ([gems params path]
   (let [[gems local] (debug= gems params path)]
     (println (:pretty/txt local))
     [gems params])))
