(ns spark.eval
  (:require [spark.paths :as paths]
            [spark.util :as util]))

(defn eval-func
  [gems params-stack]
  (let [func (util/get-param params-stack :func)
        func (if (string? func)
               (symbol func)
               func)
        func (if (symbol? func)
               (resolve func)
               func)]
    (func gems params-stack)))

(defn eval-request
  [gems params-stack]
  (let [request (get-in gems (paths/request-path params-stack))
        params-stack (conj params-stack request)
        [gems params-stack] (eval-func gems [params-stack])]
    [gems (pop params-stack)]))

(defn eval-script
  [gems params-stack]
  (let [params-stack (conj params-stack {})
        script (get-param params-stack :script)
        [gems params-stack] (reduce
                              (fn [[gems params-stack] [req-key operation]]
                                (eval-request gems
                                              (conj params-stack operation)))
                              [gems params-stack]
                              script)
        params-stack (pop params-stack)]
    [gems params-stack]))
