(ns status-im.utils.number 
  (:require [clojure.string :as clojure-string]))

(defn numerical-string
  "Returns 0 for strings that are used as numbers usually
   coming from text fields"
  [x]
  (if (or (clojure-string/blank? x)
          (js/isNaN x))
    0
    (js/parseInt x)))