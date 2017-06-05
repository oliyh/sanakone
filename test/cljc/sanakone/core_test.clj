(ns sanakone.core-test
  (:require [sanakone.core :as kone]
            [clojure.test :refer [deftest is testing]]))

(deftest conjugate-test

  (testing "nil yields nil"
    (is (nil? (kone/conjugate nil {:person :first-singular})))
    (is (nil? (kone/conjugate "" {:person :first-singular}))))

  (testing "present tense"
    (testing "type one verbs"
      (testing "first person"
        (is (= {:infinitive "laulaa"
                :rule-id :type-one-verb
                :rule-name "Type 1 verb"
                :person :first-singular
                :translation "to sing"
                :pronoun "minä"
                :conjugated "laulan"
                :word-parts [{:word-type :infinitive
                              :text "laulaa"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "laula"
                              :description "Remove the infinitive marker 'a'"}
                             {:word-type :consonant-gradation
                              :text "laula"
                              :description "Apply consonant gradation (strong-weak)"}
                             {:word-type :personal-ending
                              :text "laulan"
                              :description "The personal ending for first-singular is 'n'"}]}

               (kone/conjugate "laulaa" {:person :first-singular}))))

      (testing "second person"
        (is (= {:infinitive "laulaa"
                :rule-id :type-one-verb
                :rule-name "Type 1 verb"
                :person :second-singular
                :translation "to sing"
                :pronoun "sinä"
                :conjugated "laulat"
                :word-parts [{:word-type :infinitive
                              :text "laulaa"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "laula"
                              :description "Remove the infinitive marker 'a'"}
                             {:word-type :consonant-gradation
                              :text "laula"
                              :description "Apply consonant gradation (strong-weak)"}
                             {:word-type :personal-ending
                              :text "laulat"
                              :description "The personal ending for second-singular is 't'"}]}

               (kone/conjugate "laulaa" {:person :second-singular}))))

      (testing "third person"
        (is (= {:infinitive "laulaa"
                :rule-id :type-one-verb
                :rule-name "Type 1 verb"
                :person :third-singular
                :translation "to sing"
                :pronoun "hän"
                :conjugated "laulaa"
                :word-parts [{:word-type :infinitive
                              :text "laulaa"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "laula"
                              :description "Remove the infinitive marker 'a'"}
                             {:word-type :consonant-gradation
                              :text "laula"
                              :description "Apply consonant gradation (strong-weak)"}
                             {:word-type :personal-ending
                              :text "laulaa"
                              :description "The last vowel is lengthened for third-singular of Type 1 verb"}]}

               (kone/conjugate "laulaa" {:person :third-singular})))))

    (testing "type two verbs"
      (testing "first person"
        (is (= {:infinitive "juoda"
                :rule-id :type-two-verb
                :rule-name "Type 2 verb"
                :person :first-singular
                :translation "to drink"
                :pronoun "minä"
                :conjugated "juon"
                :word-parts [{:word-type :infinitive
                              :text "juoda"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "juo"
                              :description "Remove the infinitive marker 'da'"}
                             {:word-type :personal-ending
                              :text "juon"
                              :description "The personal ending for first-singular is 'n'"}]}

               (kone/conjugate "juoda" {:person :first-singular}))))

      (testing "second person"
        (is (= {:infinitive "juoda"
                :rule-id :type-two-verb
                :rule-name "Type 2 verb"
                :person :second-singular
                :translation "to drink"
                :pronoun "sinä"
                :conjugated "juot"
                :word-parts [{:word-type :infinitive
                              :text "juoda"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "juo"
                              :description "Remove the infinitive marker 'da'"}
                             {:word-type :personal-ending
                              :text "juot"
                              :description "The personal ending for second-singular is 't'"}]}

               (kone/conjugate "juoda" {:person :second-singular}))))

      (testing "third person"
        (is (= {:infinitive "juoda"
                :rule-id :type-two-verb
                :rule-name "Type 2 verb"
                :person :third-singular
                :translation "to drink"
                :pronoun "hän"
                :conjugated "juo"
                :word-parts [{:word-type :infinitive
                              :text "juoda"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "juo"
                              :description "Remove the infinitive marker 'da'"}
                             {:word-type :personal-ending
                              :text "juo"
                              :description "There is no lengthening of the third-singular of Type 2 verb"}]}

               (kone/conjugate "juoda" {:person :third-singular})))))

    (testing "type three verbs"
      (testing "first person"
        (is (= {:infinitive "opiskella"
                :rule-id :type-three-verb
                :rule-name "Type 3 verb"
                :person :first-singular
                :translation "to study something"
                :pronoun "minä"
                :conjugated "opiskelen"
                :word-parts [{:word-type :infinitive
                              :text "opiskella"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "opiskel"
                              :description "Remove the infinitive marker 'la'"}
                             {:word-type :consonant-gradation
                              :text "opiskel"
                              :description "Apply consonant gradation (weak-strong)"}
                             {:word-type :vowel-addition
                              :text "opiskele"
                              :description "Add 'e' before the personal ending"}
                             {:word-type :personal-ending
                              :text "opiskelen"
                              :description "The personal ending for first-singular is 'n'"}]}

               (kone/conjugate "opiskella" {:person :first-singular}))))

      (testing "second person"
        (is (= {:infinitive "opiskella"
                :rule-id :type-three-verb
                :rule-name "Type 3 verb"
                :person :second-singular
                :translation "to study something"
                :pronoun "sinä"
                :conjugated "opiskelet"
                :word-parts [{:word-type :infinitive
                              :text "opiskella"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "opiskel"
                              :description "Remove the infinitive marker 'la'"}
                             {:word-type :consonant-gradation
                              :text "opiskel"
                              :description "Apply consonant gradation (weak-strong)"}
                             {:word-type :vowel-addition
                              :text "opiskele"
                              :description "Add 'e' before the personal ending"}
                             {:word-type :personal-ending
                              :text "opiskelet"
                              :description "The personal ending for second-singular is 't'"}]}

               (kone/conjugate "opiskella" {:person :second-singular}))))

      (testing "third person"
        (is (= {:infinitive "opiskella"
                :rule-id :type-three-verb
                :rule-name "Type 3 verb"
                :person :third-singular
                :translation "to study something"
                :pronoun "hän"
                :conjugated "opiskelee"
                :word-parts [{:word-type :infinitive
                              :text "opiskella"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "opiskel"
                              :description "Remove the infinitive marker 'la'"}
                             {:word-type :consonant-gradation
                              :text "opiskel"
                              :description "Apply consonant gradation (weak-strong)"}
                             {:word-type :vowel-addition
                              :text "opiskele"
                              :description "Add 'e' before the personal ending"}
                             {:word-type :personal-ending
                              :text "opiskelee"
                              :description "The last vowel is lengthened for third-singular of Type 3 verb"}]}

               (kone/conjugate "opiskella" {:person :third-singular})))))

    (testing "type four verbs"
      (testing "first person"
        (is (= {:infinitive "pelata"
                :rule-id :type-four-verb
                :rule-name "Type 4 verb"
                :person :first-singular
                :translation "to play"
                :pronoun "minä"
                :conjugated "pelaan"
                :word-parts [{:word-type :infinitive
                              :text "pelata"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "pela"
                              :description "Remove the infinitive marker 'ta'"}
                             {:word-type :consonant-gradation
                              :text "pela"
                              :description "Apply consonant gradation (weak-strong)"}
                             {:word-type :vowel-addition
                              :text "pelaa"
                              :description "Add 'a' before the personal ending"}
                             {:word-type :personal-ending
                              :text "pelaan"
                              :description "The personal ending for first-singular is 'n'"}]}

               (kone/conjugate "pelata" {:person :first-singular}))))

      (testing "second person"
        (is (= {:infinitive "pelata"
                :rule-id :type-four-verb
                :rule-name "Type 4 verb"
                :person :second-singular
                :translation "to play"
                :pronoun "sinä"
                :conjugated "pelaat"
                :word-parts [{:word-type :infinitive
                              :text "pelata"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "pela"
                              :description "Remove the infinitive marker 'ta'"}
                             {:word-type :consonant-gradation
                              :text "pela"
                              :description "Apply consonant gradation (weak-strong)"}
                             {:word-type :vowel-addition
                              :text "pelaa"
                              :description "Add 'a' before the personal ending"}
                             {:word-type :personal-ending
                              :text "pelaat"
                              :description "The personal ending for second-singular is 't'"}]}

               (kone/conjugate "pelata" {:person :second-singular}))))

      (testing "third person"
        (is (= {:infinitive "pelata"
                :rule-id :type-four-verb
                :rule-name "Type 4 verb"
                :person :third-singular
                :translation "to play"
                :pronoun "hän"
                :conjugated "pelaa"
                :word-parts [{:word-type :infinitive
                              :text "pelata"
                              :description "The infinitive"}
                             {:word-type :stem
                              :text "pela"
                              :description "Remove the infinitive marker 'ta'"}
                             {:word-type :consonant-gradation
                              :text "pela"
                              :description "Apply consonant gradation (weak-strong)"}
                             {:word-type :vowel-addition
                              :text "pelaa"
                              :description "Add 'a' before the personal ending"}
                             {:word-type :personal-ending
                              :text "pelaa"
                              :description "The last vowel is lengthened for third-singular of Type 4 verb"}]}

               (kone/conjugate "pelata" {:person :third-singular})))))

    (testing "type five verbs"
      (testing "first person"
        (is (= {:infinitive "tarvita"
                :rule-id :type-five-verb
                :rule-name "Type 5 verb"
                :person :first-singular
                :translation "to need"
                :pronoun "minä"
                :conjugated "tarvitsen"
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
                              :description "The personal ending for first-singular is 'n'"}]}

               (kone/conjugate "tarvita" {:person :first-singular}))))

      (testing "second person"
        (is (= {:infinitive "tarvita"
                :rule-id :type-five-verb
                :rule-name "Type 5 verb"
                :person :second-singular
                :translation "to need"
                :pronoun "sinä"
                :conjugated "tarvitset"
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
                              :text "tarvitset"
                              :description "The personal ending for second-singular is 't'"}]}

               (kone/conjugate "tarvita" {:person :second-singular}))))

      (testing "third person"
        (is (= {:infinitive "tarvita"
                :rule-id :type-five-verb
                :rule-name "Type 5 verb"
                :person :third-singular
                :translation "to need"
                :pronoun "hän"
                :conjugated "tarvitsee"
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
                              :text "tarvitsee"
                              :description "The last vowel is lengthened for third-singular of Type 5 verb"}]}

               (kone/conjugate "tarvita" {:person :third-singular}))))))

  (testing "vowel harmony"
    (is (= "herään" (:conjugated (kone/conjugate "herätä" {:person :first-singular}))))
    (is (= "hävitsen" (:conjugated (kone/conjugate "hävitä" {:person :first-singular})))))

  (testing "consonant gradation"
    (testing "strong to weak"
      (testing "in type 1 verbs"
        (is (= "tiedan" (:conjugated (kone/conjugate "tietaa" {:person :first-singular}))))))

    (testing "weak to strong"
      (testing "in type 3 verbs"
        (is (= "ajattelen" (:conjugated (kone/conjugate "ajatella" {:person :first-singular})))))

      (testing "in type 4 verbs"
        (is (= "tapaan" (:conjugated (kone/conjugate "tavata" {:person :first-singular}))))))))
