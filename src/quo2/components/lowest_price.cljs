(ns quo2.components.lowest-price
  (:require [quo.design-system.colors :as colors]
            [quo.react-native :as rn]
            [re-frame.core :as re-frame]
            [status-im.utils.number :as number-util]))

(defn lowest-price
  [{:keys [top-value
           bottom-value
           margin-top
           top-value-text-color
           top-value-bg-color
           bottom-value-bg-color
           bottom-value-text-color]}]
  (let [{:keys [height width]} @(re-frame/subscribe [:dimensions/window])]
    [rn/view {:style {:display :flex
                      :flex-direction :column
                      :width "100%"
                      :margin-top (number-util/numerical-string margin-top)
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
                :background-color top-value-bg-color}
       [rn/text {:style {:color top-value-text-color}} top-value]]]
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
                :background-color bottom-value-bg-color}
       [rn/text {:style {:color bottom-value-text-color}} bottom-value]]]]))