(ns spark.pretty
  (:require [clojure.string :as string]
            [spark.util :as util]))

(defn asString=
  [gems params]
  (let [prefix (get params :prefix "")
        unprefix (util/blanks (count prefix))
        value (:value params)]
    (cond
      :else
      (let [lines [(string/join [prefix (pr-str value)])]]
      [gems (into params {:pretty/lines lines})]))))

(defn debug=
  [gems params]
  (let [path (:path params)
        value (get-in gems path)
        local (into params {:value  value
                             :prefix ""})
        [gems local] (asString= gems local)
        txt (string/join "\n" (into ["---"] (:pretty/lines local)))]
    [gems (into params {:pretty/txt txt})]))

(defn debug
  [gems params]
  (let [[gems local] (debug= gems params)]
    (println (:pretty/txt local))
    [gems params]))
