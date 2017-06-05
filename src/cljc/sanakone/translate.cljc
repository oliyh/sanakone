(ns sanakone.translate
  (:require #?(:clj  [sanakone.macros :refer [defdata]]
               :cljs [sanakone.macros :refer-macros [defdata]])
            [clojure.string :as string]))

(defdata verbs "verbs.edn")

(defn verb [finnish]
  (when-let [english (get verbs (string/lower-case finnish))]
    (str "to " english)))
