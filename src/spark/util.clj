(ns spark.util
  (:require [clojure.string :as string]))

(defn params-into
  [params-stack index params]
  (let [index (if (< index    0)
                (- (count params-stack) index)
                index)
        old-params (get params-stack index)
        revised-params (into old-params params)]
    (assoc params-stack index revised-params)))

(defn params-replace
  [params-stack index params]
  (let [index (if (< index    0)
                (- (count params-stack) index)
                index)]
    (assoc params-stack index params)))

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
