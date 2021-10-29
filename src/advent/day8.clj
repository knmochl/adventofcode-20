(ns advent.day8
  (:require [advent.util :refer [slurp-lines]]))

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
