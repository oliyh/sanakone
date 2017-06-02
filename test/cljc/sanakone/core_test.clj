(ns sanakone.core-test
  (:require [sanakone.core :as kone]
            [clojure.test :refer [deftest is testing]]))

(deftest conjugate-test

  (testing "present tense"
    (testing "type one verbs"
      (is (= {:infinitive "laulaa"
              :rule-name "Type 1 verb"
              :person :first-singular
              :pronoun "minä"
              :word-parts [{:word-type :infinitive
                            :text "laulaa"
                            :description "The infinitive"}
                           {:word-type :stem
                            :text "laula"
                            :description "Remove the infinitive marker 'a'"}
                           {:word-type :personal-ending
                            :text "laulan"
                            :description "The personal ending for first-singular is n"}]}

             (kone/conjugate "laulaa" {:person :first-singular}))))))
