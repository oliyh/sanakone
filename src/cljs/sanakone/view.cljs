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
      (let [{:keys [rule-name infinitive pronoun word-parts]} @result]
        [:div
         [:div
          [:h3 infinitive]
          [:strong rule-name]]
         [:div.word-result
          [:span.pronoun {:title "pronoun"} pronoun]
          " "
          (for [{:keys [word-type text description]} word-parts]
            ^{:key word-type}
            [:div.word-part {:title (name word-type)
                             :class (name word-type)}
             [:div text]
             [:span.description
              (map-indexed (fn [i d]
                             ^{:key i}
                             [:div d])
                           description)]])]]))))

(defn home []
  (re-frame/dispatch-sync [::model/init])
  (fn []
    [:div
     [:h1 "sanakone"]
     [:div
      [text-input]
      [result]]]))
