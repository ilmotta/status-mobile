(ns quo2.screens.lowest-price
  (:require [quo.design-system.colors :as colors]
            [quo.react-native :as rn]
            [re-frame.core :as re-frame]))


(defn render-item
  [{:keys [value1 value2]}]
  
  (let [{:keys [height width]} @(re-frame/subscribe [:dimensions/window])] 
    [rn/view {:style {:display :flex
                      :flex-direction :column
                      :height "100%"
                      :justify-content :center}}
     [rn/view {:style {:overflow :hidden}}
      [rn/view {:style {:border-width 2
                        :border-color (:text-01 @colors/theme)
                        :border-radius 1
                        :margin -2
                        :top (* 0.017 height)
                        :bottom (* 0.01 height)
                        :opacity 0.4
                        :margin-bottom 0
                        :border-style :dotted}}]
      [rn/view {:z-index 1
                :margin-left (* 0.10 width)
                :align-self :flex-start
                :padding 6
                :border-radius 3
                :background-color (:ui-01 @colors/theme)}
       [rn/text {:style {:color (:text-01 @colors/theme)}} value1]]]
     [rn/view {:style {:overflow :hidden
                       :margin-top 15}}
      [rn/view {:style {:border-width 2
                        :border-color (:text-01 @colors/theme)
                        :border-radius 1
                        :margin -2
                        :top (* 0.017 height)
                        :bottom (* 0.01 height)
                        :opacity 0.4
                        :margin-bottom 0
                        :border-style :dotted}}]
      [rn/view {:z-index 1
                :align-self :flex-start
                :margin-left (* 0.75 width)
                :border-radius 3
                :padding 6
                :background-color (:ui-01 @colors/theme)}
       [rn/text {:style {:color (:text-01 @colors/theme)}} value2]]]]))

(defn preview-lowest
  []
  [render-item {:value1 "$2,130.56"
                :value2 "$2,130.56"}])