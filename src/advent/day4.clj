(ns advent.day4
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]))

(def test-input [
 "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
 "byr:1937 iyr:2017 cid:147 hgt:183cm"
 ""
 "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884"
 "hcl:#cfa07d byr:1929"
 ""
 "hcl:#ae17e1 iyr:2013"
 "eyr:2024"
 "ecl:brn pid:760753108 byr:1931"
 "hgt:179cm"
 ""
 "hcl:#cfa07d eyr:2025 pid:166559648"
 "iyr:2011 ecl:brn hgt:59in"
  ])

(def required-fields
  [:byr :ecl :eyr :hcl :hgt :iyr :pid])

(defn split-passports
  [passport-lines]
  (string/split-lines (string/join " " (mapv #(if (= "" %) "\n" %) passport-lines))))

(defn make-kv-pair
  [[keyname value]]
  [(keyword keyname) value])

(defn make-passport
  [passport]
  (into {}
        (mapv make-kv-pair
              (mapv #(string/split % #":")
                    (string/split (string/trim passport) #" ")))))

(defn parse-passports
  [input]
  (mapv make-passport (split-passports input)))

(defn passport-valid?
  [passport]
  (= 0 (count (filter nil? (map #(% passport) required-fields)))))

(defn part1
  []
  (count (filter true? (mapv passport-valid? (parse-passports (slurp-lines "input4.txt"))))))
