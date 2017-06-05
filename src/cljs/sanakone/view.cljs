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

(defn- suggestion [word]
  [:a {:href "#" :on-click #(re-frame/dispatch [::model/input-text word])} word])

(defn- person-choice []
  (let [choices (re-frame/subscribe [::model/pronouns])]
    (fn []
      [:div.mdl-tabs.is-upgraded
       [:div.mdl-tabs__tab-bar
        (for [{:keys [person pronoun selected?]} @choices]
          ^{:key person}
          [:a.mdl-tabs__tab
           {:on-click #(re-frame/dispatch [::model/person-choice person])
            :class (when selected? "is-active")}
           pronoun
           [:span.mdl-tabs__ripple-container.mdl-js-ripple-effect
            [:span.mdl-ripple]]])]])))

(defn- text-input []
  (let [text (re-frame/subscribe [::model/input-text])]
    (fn []
      [:div.mdl-textfield
       [:input.mdl-textfield__input
        {:type "text"
         :value @text
         :placeholder "Type an infinitive verb e.g. laulaa"
         :on-change #(re-frame/dispatch [::model/input-text (-> % .-target .-value)])}]])))

(defn- input-card []
  [card
   [:span "sanakone"
    [:br] [:small "Finnish by rules instead of rote"]]
   [:div
    [:p "Type infinitives below to learn conjugation, such as "
     [suggestion "laulaa"] ", " [suggestion "juoda"] ", " [suggestion "opiskella"] ", " [suggestion "pelata"] " or " [suggestion "tarvita"]]
    [text-input]]])

(defn- result []
  (let [result (re-frame/subscribe [::model/result])]
    (fn []
      (when-let [{:keys [rule-name infinitive translation pronoun conjugated word-parts]}
                 @result]
        [card
         [:div infinitive (when translation [:small (str " (" translation ")")])
          [:span.rule-name [:small rule-name]]]
         [:div
          [person-choice]
          [:div.word-result
           [:div.conjugated
            [:span.pronoun {:title "pronoun"} pronoun]
            " "
            [:span {:title "conjugated"} conjugated]]
           [:div.transforms
            (for [{:keys [word-type text description]} word-parts]
              ^{:key word-type}
              [:div {:title (name word-type)
                     :class (name word-type)}
               [:div.description description ":"]
               [:div.text text]])]]]]))))

(defn home []
  (re-frame/dispatch-sync [::model/init])
  (fn []
    [:div
     [:div.mdl-grid
      [input-card]
      [result]]]))
