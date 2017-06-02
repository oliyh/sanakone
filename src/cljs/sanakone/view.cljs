(ns sanakone.view
  (:require [sanakone.model :as model]
            [sanakone.core :as kone]
            [re-frame.core :as re-frame]))

(defn- card [title content & [actions]]
  [:div.mdl-card.mdl-shadow--2dp.mdl-cell.mdl-cell--6-col-desktop.mdl-cell--3-offset-desktop.mdl-cell--6-col-tablet.mdl-cell--1-offset-tablet.mdl-cell--12-col-phone
   [:div.mdl-card__title
    [:h2.mdl-card__title-text title]]
   [:div.mdl-card__supporting-text content]
   (when actions
     [:div.mdl-card__actions.mdl-card--border actions])])

(defn- text-input []
  (let [text (re-frame/subscribe [::model/input-text])]
    (fn []
      [card
       "sanakone"
       [:div
        [:p "sanakone is a resource for learning Finnish by rules instead of rote"]
        [:div.mdl-textfield
         [:input.mdl-textfield__input
          {:type "text"
           :value @text
           :placeholder "Type an infinitive verb e.g. laulaa"
           :on-change #(re-frame/dispatch [::model/input-text (-> % .-target .-value)])}]]]])))

(defn- result []
  (when-let [result (re-frame/subscribe [::model/result])]
    (fn []
      (let [{:keys [rule-name infinitive pronoun word-parts]} @result]
        [card
         [:span infinitive [:br] [:small rule-name]]
         [:div
          [:div.word-result
           [:span.pronoun {:title "pronoun"} pronoun]
           " "
           (for [{:keys [word-type text description]} word-parts]
             ^{:key word-type}
             [:div.word-part {:title (name word-type)
                              :class (name word-type)}
              [:div text]
              [:span.description
               description]])]]]))))

(defn home []
  (re-frame/dispatch-sync [::model/init])
  (fn []
    [:div
     [:div.mdl-grid
      [text-input]
      [result]]]))
