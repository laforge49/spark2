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
