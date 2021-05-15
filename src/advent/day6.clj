(ns advent.day6
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.set :as set]))

(def test-input
  ["abc"
   ""
   "a"
   "b"
   "c"
   ""
   "ab"
   "ac"
   ""
   "a"
   "a"
   "a"
   "a"
   ""
   "b"]
  )

(defn split-groups
  [question-lines]
  (filter #(not= '("") %)
          (partition-by #(= "" %) question-lines)))

(defn combine-questions
  [question-list]
  (map set question-list))

(defn anyone-answered
  [question-sets]
  (apply set/union question-sets))

(defn count-answers
  [answer-set]
  (count answer-set))

(defn part1
  []
  (reduce + (map (comp count-answers anyone-answered combine-questions)
                 (split-groups (slurp-lines "input6.txt")))))
