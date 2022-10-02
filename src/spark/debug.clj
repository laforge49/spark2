(ns spark.debug
  (:require
    [spark.boot :as boot]
    [spark.eval :as eval]
    [spark.kws :as kws]
    ))

(defn ribbit
  [env]
  (println "Ribbit!"))

(defn print-value
  [env]
  (println (:param/value env)))