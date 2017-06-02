(ns sanakone.translate
  (:require [sanakone.macros :refer [defdata]]
            [clojure.string :as string]))

(defdata verbs "verbs.edn")

(defn verb [finnish]
  (get verbs (string/lower-case finnish)))
