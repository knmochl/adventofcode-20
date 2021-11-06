(ns advent.day10
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input [28 33 18 42 31 14 46 20 48 47 24 23 49 45 19 38 39 11 1 32 25 35 8 17 7 9 4 2 34 10 3])

(defn get-differences
  [coll]
  (let [adaptors (sort coll)
        device (+ 3 (last adaptors))]
    (map -
         (conj (vec adaptors) device)
         (cons 0 adaptors))))

(defn count-runs
  [coll result]
  (let [drop-threes (drop-while (partial = 3) coll)
        run (count (take-while (partial = 1) drop-threes))
        rest (drop-while (partial = 1) drop-threes)]
    (if (empty? rest)
      result
      (count-runs rest (conj result run)))))

(defn possible-combos
  [run-length]
  (+ 1 (/ (* run-length (dec run-length)) 2)))

(defn part1
  []
  (let [adaptors (map edn/read-string (slurp-lines "input10.txt"))
        differences (frequencies (get-differences adaptors))]
    (* (differences 1) (differences 3))))

(defn part2
  []
  (let [adaptors (map edn/read-string (slurp-lines "input10.txt"))
        differences (get-differences adaptors)]
    (apply * (map possible-combos (count-runs differences [])))))
