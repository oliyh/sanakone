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

             (kone/conjugate "opiskella" {:person :first-singular}))))

    (testing "type four verbs"
      (is (= {:infinitive "pelata"
              :rule-name "Type 4 verb"
              :person :first-singular
              :translation "play"
              :pronoun "minä"
              :word-parts [{:word-type :infinitive
                            :text "pelata"
                            :description "The infinitive"}
                           {:word-type :stem
                            :text "pela"
                            :description "Remove the infinitive marker 'ta'"}
                           {:word-type :vowel-addition
                            :text "pelaa"
                            :description "Add 'a' before the personal ending"}
                           {:word-type :personal-ending
                            :text "pelaan"
                            :description "The personal ending for first-singular is n"}]}

             (kone/conjugate "pelata" {:person :first-singular}))))

    (testing "type five verbs"
      (is (= {:infinitive "tarvita"
              :rule-name "Type 5 verb"
              :person :first-singular
              :translation "need"
              :pronoun "minä"
              :word-parts [{:word-type :infinitive
                            :text "tarvita"
                            :description "The infinitive"}
                           {:word-type :stem
                            :text "tarvit"
                            :description "Remove the infinitive marker 'a'"}
                           {:word-type :vowel-addition
                            :text "tarvitse"
                            :description "Add 'se' before the personal ending"}
                           {:word-type :personal-ending
                            :text "tarvitsen"
                            :description "The personal ending for first-singular is n"}]}

             (kone/conjugate "tarvita" {:person :first-singular})))))

  (testing "vowel harmony"
    (is (= "herään" (-> (kone/conjugate "herätä" {:person :first-singular})
                        :word-parts
                        last
                        :text)))

    (is (= "hävitsen" (-> (kone/conjugate "hävitä" {:person :first-singular})
                          :word-parts
                          last
                          :text)))))
