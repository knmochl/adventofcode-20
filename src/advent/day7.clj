(ns advent.day7
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input
  ["light red bags contain 1 bright white bag, 2 muted yellow bags."
   "dark orange bags contain 3 bright white bags, 4 muted yellow bags."
   "bright white bags contain 1 shiny gold bag."
   "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags."
   "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags."
   "dark olive bags contain 3 faded blue bags, 4 dotted black bags."
   "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."
   "faded blue bags contain no other bags."
   "dotted black bags contain no other bags."])

(defn parse-bag
  [bag]
  (let [[match number color] (re-find #"(\d+) (.*) bag" bag)]
    [(edn/read-string number) color]))

(defn parse-rule
  [rule]
  (let [[color rule-list] (string/split rule #" bags contain ")
        rules (filter #(not= "no other bags" %)
                      (string/split (subs rule-list
                                          0 (dec (count rule-list)))
                                    #", "))]
      {color (map parse-bag rules)}))

(defn parse-rules
  [rule-list]
  (reduce into {} (map parse-rule rule-list)))

(defn can-hold-bag?
  [bag rule]
  (filter #(= bag (second %)) (second rule)))

(defn possible-containers
  [rules bag]
  (map first (filter #(seq ((partial can-hold-bag? bag) %)) rules)))

(defn find-containers
  [rules bag]
  (let [outer (possible-containers rules bag)]
    (concat outer
            (mapcat (partial find-containers rules) outer))))

(defn count-bags
  [rules bag]
  (let [bag-list (rules bag)]
    (reduce + (mapcat #(vector (first %) (* (first %) (count-bags rules (second %)))) bag-list))))

(defn part1
  []
  (let [rules (parse-rules (slurp-lines "input7.txt"))]
    (count (set (find-containers rules "shiny gold")))))

(defn part2
  []
  (let [rules (parse-rules (slurp-lines "input7.txt"))]
    (count-bags rules "shiny gold")))
