(ns sanakone.model
  (:require [sanakone.core :as kone]
            [re-frame.core :as re-frame]))

(def ^:private interceptors
  [re-frame/trim-v])

(re-frame/reg-event-db
 ::input-text
 interceptors
 (fn [db [text]]
   (assoc db :input-text text)))

(re-frame/reg-sub
 ::input-text
 (fn [db]
   (get db :input-text)))

(re-frame/reg-sub
 ::result
 (fn []
   [(re-frame/subscribe [::input-text])])
 (fn [[input-text]]
   (let [{:keys [word-parts] :as result}
         (kone/conjugate input-text {:person :first-singular})]
     (assoc result :conjugated (-> result :word-parts last :text)))))

(re-frame/reg-event-db
 ::init
 interceptors
 (fn [db]
   {:input-text "laulaa"}))
