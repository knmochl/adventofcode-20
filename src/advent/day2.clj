(ns advent.day2
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input
  ["1-3 a: abcde"
   "1-3 b: cdefg"
   "2-9 c: ccccccccc"
   ])

(defn parse-line
  [line]
  (let [[freq req password] (string/split line #" ")
        [low high] (string/split freq #"-")
        req (first req)]
    {:min (edn/read-string low) :max (edn/read-string high) :char req :password password}))

(defn validate-password
  [password-info]
  (let [letter-count (count (filter #(= (:char password-info) %) (:password password-info)))]
    (and (>= letter-count (:min password-info))
         (<= letter-count (:max password-info)))))

(defn validate-toboggan-password
  [password-info]
  (let [pos1 (= (:char password-info) (nth (:password password-info) (dec (:min password-info))))
        pos2 (= (:char password-info) (nth (:password password-info) (dec (:max password-info))))]
    (and (or pos1 pos2) (not (and pos1 pos2)))))

(defn part1
  []
  (let [passwords (map parse-line (slurp-lines "input2.txt"))]
    (count (filter validate-password passwords))))

(defn part2
  []
  (let [passwords (map parse-line (slurp-lines "input2.txt"))]
    (count (filter validate-toboggan-password passwords))))
