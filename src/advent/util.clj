(ns advent.util
  (:require [clojure.java.io :as jio]
            [clojure.string :as str]))

(defn slurp-lines
  [filename]
  (str/split-lines (slurp (jio/resource filename))))
