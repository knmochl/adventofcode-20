(defproject advent "0.1.0-SNAPSHOT"
  :description "Advent Of Code 2020"
  :url "https://github.com/knmochl/adventofcode-20"
  :license {:name "MIT"
            :url ""}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot advent.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
