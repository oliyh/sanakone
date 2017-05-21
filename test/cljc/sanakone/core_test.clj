(ns sanokone.core-test
  (:require [sanokone.core :as kone]
            [clojure.test :refer [deftest is testing]]))

(deftest conjugate-test

  (testing "present tense"
    (testing "type one verbs"
      (is (= {:person :first-singular
              :pronoun "minä"
              :word-parts [{:word-type :stem
                            :text "laula"}
                           {:word-type :personal-ending
                            :text "n"}]}

             (kone/conjugate "laulaa" {:person :first-singular}))))))
