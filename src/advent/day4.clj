(ns advent.day4
  (:require [advent.util :refer [slurp-lines]]
            [clojure.string :as string]
            [clojure.edn :as edn]))

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

(defn passport-has-fields?
  [passport]
  (= 0 (count (filter nil? (map #(% passport) required-fields)))))

(defn is-year?
  [year]
  (re-matches #"^\d{4}$" year))

(defn validate-year
  [year-string min-year max-year]
  (if (is-year? year-string)
    (let [year (edn/read-string year-string)]
      (and (<= min-year year) (>= max-year year)))
    nil))

(defn validate-hair-color
  [hcl]
  (re-matches #"^#[0-9a-f]{6}$" hcl))

(defn validate-passport-id
  [pid]
  (re-matches #"^\d{9}$" pid))

(defn validate-eye-color
  [ecl]
  (let [possible ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"]]
    (seq (filter #(= ecl %) possible))))

(defn validate-height
  [hgt]
  (if (re-matches #"^\d+[a-z]{2}$" hgt)
    (let [len (count hgt)
          start (- len 2)
          units (subs hgt start)
          height (edn/read-string (subs hgt 0 start))]
      (case units
        "cm" (and (<= 150 height) (>= 193 height))
        "in" (and (<= 59 height) (>= 76 height))
        false))
    false))

(defn validate-passport
  [passport]
  (boolean (and (passport-has-fields? passport)
                (validate-year (:byr passport) 1920 2002)
                (validate-year (:iyr passport) 2010 2020)
                (validate-year (:eyr passport) 2020 2030)
                (validate-height (:hgt passport))
                (validate-hair-color (:hcl passport))
                (validate-eye-color (:ecl passport))
                (validate-passport-id (:pid passport)))))

(defn part1
  []
  (count (filter true? (mapv passport-has-fields? (parse-passports (slurp-lines "input4.txt"))))))

(defn part2
  []
  (count (filter true? (mapv validate-passport (parse-passports (slurp-lines "input4.txt"))))))
