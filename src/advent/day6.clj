(ns advent.day6
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]))

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
  (string/split-lines (string/join "" (mapv #(if (= "" %) "\n" %) question-lines))))

(defn combine-questions
  [question-list]
  (set question-list))

(defn count-answers
  [answer-set]
  (count answer-set))

(defn part1
  []
  (reduce + (map (comp count-answers combine-questions)
                 (split-groups (slurp-lines "input6.txt")))))
