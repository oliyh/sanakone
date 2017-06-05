(ns sanakone.model
  (:require [sanakone.core :as kone]
            [re-frame.core :as re-frame]
            [clojure.string :as string]))

(def ^:private interceptors
  [re-frame/trim-v])

(re-frame/reg-event-db
 ::input-text
 interceptors
 (fn [db [text]]
   (assoc db :input-text (some-> text string/trim string/lower-case))))

(re-frame/reg-sub
 ::input-text
 (fn [db]
   (get db :input-text)))

(re-frame/reg-event-db
 ::person-choice
 interceptors
 (fn [db [person]]
   (assoc db :person-choice person)))

(re-frame/reg-sub
 ::person-choice
 (fn [db]
   (get db :person-choice)))

(re-frame/reg-sub
 ::result
 (fn []
   [(re-frame/subscribe [::input-text])
    (re-frame/subscribe [::person-choice])])
 (fn [[input-text person-choice]]
   (kone/conjugate input-text {:person person-choice})))

(re-frame/reg-sub
 ::pronouns
 (fn []
   [(re-frame/subscribe [::person-choice])])
 (fn [[person-choice]]
   (for [[person pronoun] kone/pronouns]
     {:person person
      :pronoun pronoun
      :selected? (= person-choice person)})))

(re-frame/reg-event-db
 ::init
 interceptors
 (fn [db]
   {:input-text ""
    :person-choice :first-singular}))
