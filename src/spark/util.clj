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

(defn eval-func
  [gems params-stack]
  (let [func (get-param params-stack :func)
        func (if (string? func)
              (symbol func)
              func)
        func (if (symbol? func)
              (resolve func)
              func)]
    (func gems params-stack)))

#_ (defn eval-reqs
  [gems params-stack]
  (let [params-stack (conj params-stack {})
        reqs (get-param params-stack :reqs)
        [gems params-stack] (reduce
                              (fn [[gems params-stack] req]
                                (if (map? req)
                                  (let [mrg (get req :into false)
                                        index (get req :index -1)]
                                    (if mrg
                                      (params-into params-stack index req)
                                      (params-replace params-stack index req)))
                                  (eval-req gems
                                            (params-into params-stack -1 {:req req}))))
                              [gems params-stack]
                              reqs)
        params-stack (pop params-stack)]
    gems params-stack))
