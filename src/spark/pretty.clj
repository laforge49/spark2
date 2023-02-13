(ns spark.pretty
  (:require [clojure.string :as string]
            [spark.util :as util]))

(defn simpleScalar=
  ([gems params]
   (simpleScalar= gems params
                  (:pretty/value params)))
  ([gems params value]
   [gems (into params {:pretty/simple [(pr-str value)]})]))

(defn prefixScalar=
  ([gems params]
   (prefixScalar= gems params
            (:pretty/prefix params)
            (:prefix/value params)))
  ([gems params prefix value]
   (let [[gems local]
         (simpleScalar= gems params value)
         sim (:pretty/simple local)
         lines [string/join [prefix sim]]]
     [gems into params {:pretty/prefixed lines}])))

(defn asString=
  ([gems params]
   (asString= gems params
              (:pretty/prefix params)
              (:pretty/value params)))
  ([gems params prefix value]
   (cond
     (map? value)
     (let [lines
           (reduce
             (fn [lines [k v]]
               (if (map? v)
                 (let [lines
                       (conj lines
                             (string/join [prefix (pr-str k) ":"]))
                       pre
                       (string/join prefix "  ")
                       lines
                       (conj lines
                             (string/join [pre (pr-str v)]))]
                   lines)
                 (conj lines
                       (string/join [prefix (pr-str k) ": " (pr-str v)]))))
             []
             value)]
       [gems (into params {:pretty/lines lines})])
     :else
     (let [[gems local]
           (prefixScalar= gems params prefix value)
           prefixed (:pretty/prefixed local)]
       [gems (into params {:pretty/lines [prefixed]})]))))

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
