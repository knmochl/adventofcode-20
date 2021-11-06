(ns advent.day9
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input
  [35 20 15 25 47 40 62 55 65 95 102 117 150 182 127 219 299 277 309 576])

(defn rolling-window
  [coll size start]
  (take size
        (drop start coll)))

(defn pair-combinations
  [coll]
  (if (empty? coll) []
      (concat
       (map vector (repeat (first coll)) (drop 1 coll))
       (pair-combinations (drop 1 coll)))))

(defn check-xmas
  [value preamble]
  (some #{value} (map (partial apply +) (pair-combinations preamble))))

(defn find-first-invalid
  [coll preamble-size]
  (when (> (count coll) preamble-size)
    (let [value (first (drop preamble-size coll))
          preamble (take preamble-size coll)]
      (if (check-xmas value preamble)
        (find-first-invalid (drop 1 coll) preamble-size)
        value))))

(defn part1
  []
  (let [codes (map edn/read-string (slurp-lines "input9.txt"))]
    (find-first-invalid codes 25)))
