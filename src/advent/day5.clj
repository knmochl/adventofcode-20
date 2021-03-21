(ns advent.day5
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input ["FBFBBFFRLR"
                 "BFFFBBFRRR"
                 "FFFBBBFRRR"
                 "BBFFBBFRLL"])

(defn split-seat-code
  [seat]
  [(subs seat 0 7) (subs seat 7 10)])

(defn binary-to-int
  [binary]
  (edn/read-string (str "2r" binary)))

(defn row-to-binary
  [row]
  (string/replace (string/replace row \F \0) \B \1))

(defn col-to-binary
  [col]
  (string/replace (string/replace col \L \0) \R \1))

(defn decode-seat
  [seat]
  (let [[row-code col-code] (split-seat-code seat)]
    [(binary-to-int (row-to-binary row-code))
     (binary-to-int (col-to-binary col-code))]))

(defn seat-id
  [[row col]]
  (+ (* row 8) col))

(defn zip-seat-pairs
  [seats]
  (map vector seats (drop 1 seats)))

(defn seats-adjacent?
  [[seat1 seat2]]
  (= 1 (Math/abs (- (seat-id seat1) (seat-id seat2)))))

(defn part1
  []
  (apply max
         (map (comp seat-id decode-seat)
              (slurp-lines "input5.txt"))))

(defn part2
  []
  (+ 1 (seat-id
        (first
         (first
          (drop-while seats-adjacent?
                      (zip-seat-pairs
                       (sort-by seat-id
                                (map decode-seat
                                     (slurp-lines "input5.txt"))))))))))
