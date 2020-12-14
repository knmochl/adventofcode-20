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

(defn find-target-pair
  [coll target]
  (let [pairs (map vector (repeat (first coll)) (rest coll))
        found-target (filter #(= target (apply + %)) pairs)]
    (if (empty? found-target)
      (find-target-pair (rest coll) target)
      (first found-target))))

(defn process-expense-report
  [report target]
  (apply * (find-target-pair report target)))

(defn part1
  []
  (let [report (map edn/read-string (slurp-lines "input1.txt"))]
    (process-expense-report report target-sum)))
