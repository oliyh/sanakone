(ns sanakone.string-utils
  (:require [clojure.string :as string]))

(defn replace-last [s find replace]
  (string/reverse (string/replace-first (string/reverse s) (string/reverse find) (string/reverse replace))))
