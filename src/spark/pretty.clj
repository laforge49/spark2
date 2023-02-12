(ns spark.pretty
  (:require [clojure.string :as string]
            [spark.util :as util]))

(defn asString=
  ([gems params]
   (asString= gems params
              (:pretty/prefix params)
              (:pretty/value params)))
  ([gems params prefix value]
   (let [unprefix (util/blanks (count prefix))]
     (let [lines
           (cond
             (map? value)
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
               value)
             :else
             [(string/join [prefix (pr-str value)])])]
       [gems (into params {:pretty/lines lines})]))))

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
