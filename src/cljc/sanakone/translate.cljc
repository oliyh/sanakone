(ns sanakone.translate
  (:require [sanakone.macros :refer-macros [defdata]]
            [clojure.string :as string]))

(defdata verbs "verbs.edn")

(defn verb [finnish]
  (get verbs (string/lower-case finnish)))
