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
              :word-parts [{:word-type :stem
                            :text "laula"
                            :description ["Remove the infinitive marker 'a'"
                                          "laulaa -> laula"]}
                           {:word-type :personal-ending
                            :text "n"
                            :description ["The personal ending for first-singular is n"]}]}

             (kone/conjugate "laulaa" {:person :first-singular}))))))
