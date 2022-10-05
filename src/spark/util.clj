(ns spark.util
  (:require [clojure.string :as string]))

(defn blanks
  [count]
  (string/join (repeat count " ")))

(defn rth
  [vec index]
  (if (< index 0)
    (nth vec (- (count vec) index))
    (nth vec index)))

(defn rassoc
  [vec index value]
  (if (< index 0)
    (assoc vec (- (count vec) index) value)
    (assoc vec index value)))

(defn get-param
  ([param-stack key]
   (get-param param-stack key nil))
  ([param-stack key not-found]
   (if (empty? param-stack)
     not-found
     (let [top (last param-stack)]
       (if (contains? top key)
         (get top key)
         (recur (pop param-stack) key not-found))))))
