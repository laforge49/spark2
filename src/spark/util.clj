(ns spark.util
  (:require [clojure.string :as string]))

(defn blanks
  [count]
  (string/join (repeat count " ")))

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

(defn eval-req
  [gems params-stack req]
  (let [req (if (string? req)
              (symbol req)
              req)
        req (if (symbol? req)
              (resolve req)
              req)]
    (req gems params-stack)))

(defn eval-reqs
  [gems params-stack reqs]
  (let [params-stack (conj params-stack {})
        [gems params-stack] (reduce
                              (fn [[gems params-stack] req]
                                (if (map? req)
                                  (params-replace params-stack (get req :index -1) req)
                                  (eval-req gems params-stack req)))
                              [gems params-stack]
                              reqs)
        params-stack (pop params-stack)]
    gems params-stack))
