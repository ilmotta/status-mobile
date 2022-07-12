(ns quo2.screens.channel-avatar
  (:require [quo.design-system.colors :as colors]
            [quo2.foundations.colors :as f-colors]
            [quo.react-native :as rn]
            [status-im.ui.components.icons.icons :as icons]))

(defn channel-avatar [{:keys [big? dark? locked? coordinates]}]
  [rn/view {:style {:width 32
                    :height 32
                    :top (:top coordinates)
                    :left (:left coordinates)
                    :border-radius 100
                    :background-color (if dark? "#131E37"
                                          "#EDF0FC")}}
   [rn/view {:style {:left 6 
                     :top 6
                     :width 20
                     :height 20}} 
    [rn/view {:style {:top 2.5
                      :left 2.5}}
     [icons/icon :main-icons/browser {:color (if dark? "white" "black")
                                      :width 15
                                      :height 15}]
     [rn/view {:style {:position :absolute
                       :left 12
                       :top 12
                       :background-color (if dark? f-colors/neutral-90
                                             "white")
                       :border-radius 100}}
      [icons/icon :main-icons/close {:color (if dark?
                                              f-colors/neutral-40
                                              f-colors/neutral-50) 
                                     :width 16 
                                     :height 16}]]]]])

(defn preview-channel-avatar []
  [rn/view
   [channel-avatar {:coordinates {:top 20
                                  :left 50}
                    :dark? true
                    :locked? true
                    :big? true}]
   [channel-avatar {:coordinates {:top 40
                                  :left 100}
                    :dark? false
                    :locked? false
                    :big? false}]])