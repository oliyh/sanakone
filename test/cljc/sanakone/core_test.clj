(ns sanakone.core-test
  (:require [sanakone.core :as kone]
            [clojure.test :refer [deftest is testing]]))

(deftest conjugate-test

  (testing "nil yields nil"
    (is (nil? (kone/conjugate nil {:person :first-singular})))
    (is (nil? (kone/conjugate "" {:person :first-singular}))))

  (testing "present tense"
    (testing "type one verbs"
      (is (= {:infinitive "laulaa"
              :rule-name "Type 1 verb"
              :person :first-singular
              :translation "sing"
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

             (kone/conjugate "laulaa" {:person :first-singular}))))

    (testing "type two verbs"
      (is (= {:infinitive "juoda"
              :rule-name "Type 2 verb"
              :person :first-singular
              :translation "drink"
              :pronoun "minä"
              :word-parts [{:word-type :infinitive
                            :text "juoda"
                            :description "The infinitive"}
                           {:word-type :stem
                            :text "juo"
                            :description "Remove the infinitive marker 'da'"}
                           {:word-type :personal-ending
                            :text "juon"
                            :description "The personal ending for first-singular is n"}]}

             (kone/conjugate "juoda" {:person :first-singular}))))

    (testing "type three verbs"
      (is (= {:infinitive "opiskella"
              :rule-name "Type 3 verb"
              :person :first-singular
              :translation "study something"
              :pronoun "minä"
              :word-parts [{:word-type :infinitive
                            :text "opiskella"
                            :description "The infinitive"}
                           {:word-type :stem
                            :text "opiskel"
                            :description "Remove the infinitive marker 'la'"}
                           {:word-type :vowel-addition
                            :text "opiskele"
                            :description "Add 'e' before the personal ending"}
                           {:word-type :personal-ending
                            :text "opiskelen"
                            :description "The personal ending for first-singular is n"}]}

             (kone/conjugate "opiskella" {:person :first-singular}))))))
