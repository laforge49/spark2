(ns spark.core
  (:require
    [clojure.stacktrace :as stacktrace]
    [spark.boot :as boot]
    )
  (:gen-class))

(defn -main
  [& args]
  (println "Start")
  (try
    (let [[env params] (boot/create-gems {} {:main/args args})])
    (println "Fin")
    (catch Exception e
      (stacktrace/print-stack-trace e))))
