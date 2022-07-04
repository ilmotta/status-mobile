(ns quo2.gesture
  (:require ["react-native-gesture-handler" :refer (GestureDetector Gesture)]
            [reagent.core :as reagent]))

(def gesture-detector (reagent/adapt-react-class GestureDetector))

(defn gesture-pan [] (.Pan Gesture))

(defn on-update [pan handler] (.onUpdate pan handler))

(defn on-start [pan handler] (.onStart pan handler))

(defn on-end [pan handler] (.onEnd pan handler))