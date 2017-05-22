(ns sanakone.view
  (:require [sanakone.model :as model]
            [sanakone.core :as kone]
            [re-frame.core :as re-frame]))

(defn- text-input []
  (let [text (re-frame/subscribe [::model/input-text])]
    (fn []
      [:input {:type "text"
               :value @text
               :placeholder "Type any infinitive verb"
               :on-change #(re-frame/dispatch [::model/input-text (-> % .-target .-value)])}])))

(defn- result []
  (when-let [result (re-frame/subscribe [::model/result])]
    (fn []
      (let [{:keys [pronoun word-parts]} @result]
        [:div.word-result
         [:span.pronoun {:title "pronoun"} pronoun]
         " "
         (for [{:keys [word-type text]} word-parts]
           ^{:key word-type}
           [:span.word-part {:title (name word-type)
                             :class (name word-type)} text])]))))

(defn home []
  (re-frame/dispatch-sync [::model/init])
  (fn []
    [:div
     [:h1 "sanakone"]
     [:div
      [text-input]
      [result]]]))
