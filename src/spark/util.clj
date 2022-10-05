(ns spark.util
  (:require [clojure.string :as string]))

(defn blanks
  [count]
  (string/join (repeat count " ")))
