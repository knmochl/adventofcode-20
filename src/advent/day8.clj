(ns advent.day8
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

(def test-input
  ["nop +0"
   "acc +1"
   "jmp +4"
   "acc +3"
   "jmp -3"
   "acc -99"
   "acc +1"
   "jmp -4"
   "acc +6"])

(defn parse-instruction
  [instruction]
  (let [[opname arg] (string/split instruction #" ")]
    [(keyword opname) (edn/read-string arg)]))

(defn make-computer
  [code]
  {:ip 0 :code code :acc 0})

(defn process-instruction
  [computer]
  (let [[opcode arg] (nth (computer :code) (computer :ip))]
    (case opcode
      :nop (update computer :ip inc)
      :jmp (update computer :ip + arg)
      :acc (update (update computer :ip inc) :acc + arg))))

(defn run-computer
  [computer visited]
  (if (contains? visited (computer :ip))
    computer
    (recur (process-instruction computer) (conj visited (computer :ip)))))

(defn part1
  []
  (let [code (map parse-instruction (slurp-lines "input8.txt"))
        computer (make-computer code)]
    (:acc (run-computer computer #{}))))
