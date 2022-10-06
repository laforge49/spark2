(ns spark.paths
  (:require [spark.util :as util]))

(defn gem-path
  [params-stack]
  [(util/get-param params-stack :gem-kw)])

(defn descriptors-path
  [params-stack]
  (conj (gem-path params-stack) :facet/descriptors))

(defn roles-path
  [params-stack]
  (conj (descriptors-path params-stack) :descriptors/roles))

(defn role-path
  [params-stack]
  (conj (roles-path params-stack) (util/get-param params-stack :role-kw)))

(defn requests-path
  [params-stack]
  (conj (role-path params-stack) :role/requests))

(defn request-path
  [params-stack]
  (conj (requests-path params-stack) (util/get-param params-stack :request-kw)))
