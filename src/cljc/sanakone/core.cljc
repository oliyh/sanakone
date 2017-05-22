(ns sanakone.core
  (:require [clojure.string :as string]))

(def pronouns
  {:first-singular "minä"})

(def personal-endings
  {:first-singular "n"})

(def type-one-verb
  {:rule-name "Type 1 verb"
   :matcher #"[aeiouyäö]{2}$"
   :stem (fn [infinitive]
           (apply str (drop-last infinitive)))
   :infinitive-marker #"[aeiouyäö]$"})

(def verb-rules
  [type-one-verb])

(defn- find-verb-rule [infinitive]
  (first (filter #(re-find (:matcher %) infinitive) verb-rules)))

(defn conjugate [infinitive {:keys [person] :as opts}]
  (when-let [{:keys [rule-name stem infinitive-marker]} (find-verb-rule infinitive)]
    {:infinitive infinitive
     :rule-name rule-name
     :person person
     :pronoun (get pronouns person)
     :word-parts [{:word-type :stem
                   :text (stem infinitive)
                   :description [(str "Remove the infinitive marker '" (first (re-find infinitive-marker infinitive)) "'")
                                 (str infinitive " -> " (stem infinitive))]}
                  {:word-type :personal-ending
                   :text (get personal-endings person)
                   :description [(str "The personal ending for " (name person) " is " (get personal-endings person))]}]}))
