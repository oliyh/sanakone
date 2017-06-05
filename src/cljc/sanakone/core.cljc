(ns sanakone.core
  (:require [sanakone.translate :as translate]
            [sanakone.string-utils :refer [replace-last]]
            [clojure.string :as string]))

(def pronouns
  {:first-singular "minä"
   :second-singular "sinä"})

(def personal-endings
  {:first-singular "n"
   :second-singular "t"})

(defn- harmonic-vowel [text vowel]
  (if (re-find #"[aou]" text)
    vowel
    (-> vowel
        (string/replace "a" "ä")
        (string/replace "o" "ö"))))

(defn- last-syllable [text]
  (rest (re-find #"(.*?)([bcdfghjklmnpqrstvwxz]+[aäeioöuy]+[bcdfghjklmnpqrstvwxz]*)$" text)))

(defn- graded-consonants [direction text]
  (let [gradations [["sk" "sk"]
                    ["tk" "tk"]
                    ["kk" "k"]
                    ["pp" "p"]
                    ["tt" "t"]
                    ["k"  ""]
                    ["p"  "v"]
                    ["t"  "d"]
                    ["lt" "ll"]
                    ["nt" "nn"]
                    ["rt" "rr"]]
        [prefix last-syllable] (last-syllable text)]
    (str
     prefix
     (reduce (fn [text [strong weak]]
               (let [[find replace] (if (= :strong-weak direction)
                                      [strong weak]
                                      [weak strong])]
                 (if (and (not= "k" strong) (re-find (re-pattern find) text))
                   (reduced (replace-last text find replace))
                   text)))
             last-syllable
             gradations))))

(defn- consonant-gradation [direction context text]
  {:word-type :consonant-gradation
   :text (graded-consonants direction text)
   :description (str "Apply consonant gradation (" (name direction) ")")})

(defn- classify-infinitive [text]
  {:word-type :infinitive
   :text text
   :description "The infinitive"})

(defn- remove-infinitive-marker [infinitive-marker context text]
  {:word-type :stem
   :text (string/replace text infinitive-marker "")
   :description (str "Remove the infinitive marker '" (first (re-find infinitive-marker text)) "'")})

(defn- vowel-addition [vowel context text]
  (let [vowel (harmonic-vowel text vowel)]
    {:word-type :vowel-addition
     :text (str text vowel)
     :description (str "Add '" vowel "' before the personal ending")}))

(defn- add-personal-ending [{:keys [person]} text]
  {:word-type :personal-ending
   :text (str text (get personal-endings person))
   :description (str "The personal ending for " (name person) " is '" (get personal-endings person) "'")})

(def type-one-verb
  {:rule-name "Type 1 verb"
   :matcher #"[aeiouyäö]{2}$"
   :transforms [(partial remove-infinitive-marker #"[aeiouyäö]$")
                (partial consonant-gradation :strong-weak)
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
                (partial consonant-gradation :weak-strong)
                (partial vowel-addition "e")
                add-personal-ending]})

(def type-four-verb
  {:rule-name "Type 4 verb"
   :matcher #"(ata|ätä|uta|ota)$"
   :transforms [(partial remove-infinitive-marker #"(ta|tä)$")
                (partial consonant-gradation :weak-strong)
                (partial vowel-addition "a")
                add-personal-ending]})

(def type-five-verb
  {:rule-name "Type 5 verb"
   :matcher #"(ita|itä)$"
   :transforms [(partial remove-infinitive-marker #"(a|ä)$")
                (partial vowel-addition "se")
                add-personal-ending]})

(def verb-rules
  [type-one-verb
   type-two-verb
   type-three-verb
   type-four-verb
   type-five-verb])

(defn- find-verb-rule [infinitive]
  (when-not (string/blank? infinitive)
    (first (filter #(re-find (:matcher %) infinitive) verb-rules))))

(defn conjugate [infinitive {:keys [person] :as opts}]
  (when-let [{:keys [rule-name transforms]} (find-verb-rule infinitive)]
    (let [input {:infinitive infinitive
                 :rule-name rule-name
                 :person person
                 :translation (translate/verb infinitive)
                 :pronoun (get pronouns person)}
          word-parts (reduce (fn [parts transform]
                               (conj parts (transform input (:text (last parts)))))
                             [(classify-infinitive infinitive)]
                             transforms)]
      (assoc input
             :word-parts word-parts
             :conjugated (-> word-parts last :text)))))
