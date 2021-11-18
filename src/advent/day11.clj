(ns advent.day11
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input
  ["L.LL.LL.LL"
   "LLLLLLL.LL"
   "L.L.L..L.."
   "LLLL.LL.LL"
   "L.LL.LL.LL"
   "L.LLLLL.LL"
   "..L.L....."
   "LLLLLLLLLL"
   "L.LLLLLL.L"
   "L.LLLLL.LL"])

(defn chart-width
  [chart]
  (count (first chart)))

(defn chart-height
  [chart]
  (count chart))

(defn get-neighbor
  [chart [x y] [dx dy]]
  (let [new-x (+ x dx)
        new-y (+ y dy)]
    (if (or (= new-x 0) (= new-y 0)
            (> new-x (chart-width chart)) (> new-y (chart-height chart)))
      \X
      (nth (nth chart (dec new-y)) (dec new-x)))))

(defn find-neighbor-in-direction
  [chart [x y] [dx dy]]
  (let [new-x (+ x dx)
        new-y (+ y dy)
        neighbor (get-neighbor chart [x y] [dx dy])]
    (case neighbor
      \L neighbor
      \# neighbor
      \X neighbor
      (find-neighbor-in-direction chart [new-x new-y] [dx dy]))))

(defn directions
  []
  (filter (partial not= [0 0])
          (for [a [-1 0 1] b [-1 0 1]] [a b])))

(defn neighbors
  [chart x y]
  (map (partial get-neighbor chart [x y]) (directions)))

(defn new-state-theory
  [chart x y]
  (let [adjacent (neighbors chart x y)
        current (get-neighbor chart [x y] [0 0])]
    (cond
      (and (= current \L) (not-any? (partial = \#) adjacent)) \#
      (and (= current \#) (<= 4 (count (filter (partial = \#) adjacent)))) \L
      :else current)))

(defn new-state-practice
  [chart x y]
  (let [current (get-neighbor chart [x y] [0 0])
        adjacent (map (partial find-neighbor-in-direction chart [x y]) (directions))]
    (cond
      (and (= current \L) (not-any? (partial = \#) adjacent)) \#
      (and (= current \#) (<= 5 (count (filter (partial = \#) adjacent)))) \L
      :else current)))

(defn step-automaton
  [algorithm chart]
  (mapv (partial apply str)
        (partition (chart-width chart)
                   (mapv #(apply algorithm chart %)
                         (for [y (range 1 (inc (chart-height chart)))
                               x (range 1 (inc (chart-width chart)))]
                           [x y])))))

(defn run-to-steady-state
  [algorithm chart]
  (let [new-chart (step-automaton algorithm chart)]
    (if (= chart new-chart)
      chart
      (run-to-steady-state algorithm new-chart))))

(defn count-occupied-seats-in-row
  [chart-row]
  (count (filter (partial = \#) chart-row)))

(defn count-occupied-seats
  [chart]
  (reduce + (map count-occupied-seats-in-row chart)))

(defn part1
  []
  (let [seating-chart (slurp-lines "input11.txt")]
    (count-occupied-seats (run-to-steady-state new-state-theory seating-chart))))

(defn part2
  []
  (let [seating-chart (slurp-lines "input11.txt")]
    (count-occupied-seats (run-to-steady-state new-state-practice seating-chart))))
