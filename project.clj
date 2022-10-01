(defproject spark2 "0.1.0-SNAPSHOT"
  :description "The AllSpark of Gems"
  :url "https://github.com/laforge49/spark2"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :repl-options {:init-ns spark2.core}
  :main ^:skip-aot spark2.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
