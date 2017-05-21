(ns sanakone.app
  (:require [reagent.core :as reagent]
            [sanakone.core :as kone]))

(def app (js/document.getElementById "app"))

(defn- home []
  [:div
   [:h1 "sanakone"]
   (let [infinitive "laulaa"
         {:keys [pronoun word-parts]}
         (kone/conjugate infinitive {:person :first-singular})]
     [:div
      [:h3 infinitive]
      [:div.word-result
       [:span.pronoun {:title "pronoun"} pronoun]
       " "
       (for [{:keys [word-type text]} word-parts]
         ^{:key word-type}
         [:span.word-part {:title (name word-type)
                           :class (name word-type)} text])]])])

(defn- mount-app []
  (reagent/render [home] app))

(defn- init []
  (mount-app))

(defn on-figwheel-reload []
  (mount-app))

(.addEventListener js/document "DOMContentLoaded" init)
