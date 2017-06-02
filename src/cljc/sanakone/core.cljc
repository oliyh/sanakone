(ns sanakone.core
  (:require [sanakone.translate :as translate]
            [clojure.string :as string]))

(def pronouns
  {:first-singular "minä"})

(def personal-endings
  {:first-singular "n"})

(defn- classify-infinitive [text]
  {:word-type :infinitive
   :text text
   :description "The infinitive"})

(defn- remove-infinitive-marker [infinitive-marker context text]
  {:word-type :stem
   :text (string/replace text infinitive-marker "")
   :description (str "Remove the infinitive marker '" (first (re-find infinitive-marker text)) "'")})

(defn- vowel-addition [vowel context text]
  {:word-type :vowel-addition
   :text (str text "e")
   :description "Add 'e' before the personal ending"})

(defn- add-personal-ending [{:keys [person]} text]
  {:word-type :personal-ending
   :text (str text (get personal-endings person))
   :description (str "The personal ending for " (name person) " is " (get personal-endings person))})

(def type-one-verb
  {:rule-name "Type 1 verb"
   :matcher #"[aeiouyäö]{2}$"
   :transforms [(partial remove-infinitive-marker #"[aeiouyäö]$")
                add-personal-ending]})

(def type-two-verb
  {:rule-name "Type 2 verb"
   :matcher #"(da|dä)$"
   :transforms [(partial remove-infinitive-marker #"(da|dä)$")
                add-personal-ending]})

(def type-three-verb
  {:rule-name "Type 3 verb"
   :matcher #"(la|lä|sta|stä)$"
   :transforms [(partial remove-infinitive-marker #"(la|lä|ta|tä)$")
                (partial vowel-addition "e")
                add-personal-ending]})

(def verb-rules
  [type-one-verb
   type-two-verb
   type-three-verb])

(defn- find-verb-rule [infinitive]
  (when-not (string/blank? infinitive)
    (first (filter #(re-find (:matcher %) infinitive) verb-rules))))

(defn conjugate [infinitive {:keys [person] :as opts}]
  (when-let [{:keys [rule-name transforms]} (find-verb-rule infinitive)]
    (let [input {:infinitive infinitive
                 :rule-name rule-name
                 :person person
                 :translation (translate/verb infinitive)
                 :pronoun (get pronouns person)}]
      (assoc input
             :word-parts (reduce (fn [parts transform]
                                   (conj parts (transform input (:text (last parts)))))
                                 [(classify-infinitive infinitive)]
                                 transforms)))))
