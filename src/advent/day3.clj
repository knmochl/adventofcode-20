(ns advent.day3
  (:require [advent.util :refer [slurp-lines]]))

(def test-input
  ["..##......."
   "#...#...#.."
   ".#....#..#."
   "..#.#...#.#"
   ".#...##..#."
   "..#.##....."
   ".#.#.#....#"
   ".#........#"
   "#.##...#..."
   "#...##....#"
   ".#..#...#.#"
   ])

(defn tree-at
  [trees x y]
  (let [x-size (count (first trees))
        y-size (count trees)
        wrap-loc (mod (dec x) x-size)]
    (= \# (nth (nth trees (dec y)) wrap-loc))))

(defn coord-path
  [trees dx dy]
  (letfn [(move [[x y]] [(+ x dx) (+ y dy)])]
    (take-while #(<= (second %) (count trees)) (iterate move [1 1]))))

(defn trees-on-path
  [trees coords]
  (map (partial apply tree-at trees) coords))

(defn count-trees
  [trees dx dy]
  (count (filter true? (trees-on-path trees (coord-path trees dx dy)))))

(defn part1
  []
  (let [trees (slurp-lines "input3.txt")]
    (count-trees trees 3 1)))

(defn part2
  []
  (let [trees (slurp-lines "input3.txt")
        slopes [[1 1] [3 1] [5 1] [7 1] [1 2]]]
    (apply * (map (partial apply count-trees trees) slopes))))
