(ns advent.day1
  (:require [advent.util :refer [slurp-lines]]
            [clojure.edn :as edn]))

(def test-input
  [1721
   979
   366
   299
   675
   1456])

(def target-sum 2020)

(defn combinations
  [m coll]
  (letfn [(comb-aux
            [m drop-num]
            (if (= 1 m)
              (for [x (drop drop-num coll)]
                (list x))
              (for [c (range drop-num (count coll))
                    xs (comb-aux (dec m) (inc c))]
                (cons (nth coll c) xs))))]
    (comb-aux m 0)))

(defn find-target-combo
  [coll size target]
  (let [candidates (combinations size coll)
        found-target (filter #(= target (apply + %)) candidates)]
    (first found-target)))

(defn process-expense-report
  [report combo-size target]
  (apply * (find-target-combo report combo-size target)))

(defn part1
  []
  (let [report (map edn/read-string (slurp-lines "input1.txt"))]
    (process-expense-report report 2 target-sum)))

(defn part2
  []
  (let [report (map edn/read-string (slurp-lines "input1.txt"))]
    (process-expense-report report 3 target-sum)))
