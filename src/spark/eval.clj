(ns spark.eval
  (:require [spark.kws :as kws]))

(defn function-eval
  [env]
  (let [function-name (:param/function-name env)
        function-symbol (symbol function-name)
        function (resolve function-symbol)
        ]
    (function env)
    ))

(defn gem-eval
  [env]
  (let [request-kw (:param/request env)
        gem (kws/env-gem env)
        requests (get-in @gem (kws/gem-requests-kws env))
        request (request-kw requests)
        env (into env {:param/function-name (:eval/function-name request)})
        env (into env request)
        ]
    (function-eval env)
    ))
