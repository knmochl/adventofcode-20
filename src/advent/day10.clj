(ns advent.day10
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input [28 33 18 42 31 14 46 20 48 47 24 23 49 45 19 38 39 11 1 32 25 35 8 17 7 9 4 2 34 10 3])

(defn get-differences
  [coll]
  (let [adaptors (sort coll)
        device (+ 3 (last adaptors))]
    (frequencies
     (map -
          (conj (vec adaptors) device)
          (cons 0 adaptors)))))

(defn part1
  []
  (let [adaptors (map edn/read-string (slurp-lines "input10.txt"))
        differences (get-differences adaptors)]
    (* (differences 1) (differences 3))))
