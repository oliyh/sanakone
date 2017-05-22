(ns sanakone.app
  (:require [reagent.core :as reagent]
            [sanakone.view :as view]))

(def app (js/document.getElementById "app"))


(defn- mount-app []
  (reagent/render [view/home] app))

(defn- init []
  (mount-app))

(defn on-figwheel-reload []
  (mount-app))

(.addEventListener js/document "DOMContentLoaded" init)
