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
    (let [env (boot/create-gems {} {})])
    (println "Fin")
    (catch Exception e
      (stacktrace/print-stack-trace e))))
