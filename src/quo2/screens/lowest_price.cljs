(ns quo2.screens.lowest-price
  (:require [quo2.foundations.colors :as f-colors]
            [quo.react-native :as rn]
            [quo.previews.preview :as preview] 
            [quo2.components.lowest-price :as quo2]
            [reagent.core :as reagent]))

(def descriptor [{:label "Top value"
                  :key :top-value
                  :type :text}
                 {:label "Top value background color"
                  :key :top-value-bg-color
                  :type :text}
                 {:label "Top value text color"
                  :key :top-value-text-color
                  :type :text}
                 {:label "Bottom value"
                  :key :bottom-value
                  :type :text}
                 {:label "Bottom value background color"
                  :key :bottom-value-bg-color
                  :type :text}
                 {:label "Bottom value text color"
                  :key :bottom-value-text-color
                  :type :text}
                 {:label "Margin top"
                  :key :margin-top
                  :type :text}])

(defn cool-preview []
  (let [state (reagent/atom {})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [rn/view {:flex 1}
        [preview/customizer state descriptor]]
       [rn/view {:padding-vertical 60
                 :flex-direction   :row
                 :justify-content  :center}
        [quo2/lowest-price @state]]])))

(defn preview-lowest-price []
  [rn/view {:background-color (f-colors/theme-colors f-colors/white
                                                   f-colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
