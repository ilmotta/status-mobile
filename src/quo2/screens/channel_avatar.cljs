(ns quo2.screens.channel-avatar
  (:require [quo.design-system.colors :as colors]
            [quo2.foundations.colors :as f-colors]
            [quo.react-native :as rn]
            [status-im.ui.components.icons.icons :as icons]))

(defn channel-avatar [{:keys [big? dark? icon coordinates]}]
  [rn/view {:style {:width (if big? 32 24)
                    :height (if big? 32 24)
                    :top (:top coordinates)
                    :left (:left coordinates)
                    :border-radius 100
                    :background-color (if dark? "#131E37"
                                          "#EDF0FC")}}
   [rn/view {:style {:left (if big? 6 3)
                     :top (if big? 6 3)
                     :width 20
                     :height 20}}
    [rn/view {:style {:top 2.5
                      :left 2.5}}
     [icons/icon :main-icons/peach {:color "nil"
                                    :width 15
                                    :height 15}]
     (when (not= icon :none)
       [rn/view {:style {:position :absolute
                         :left (if big? 12 6)
                         :top (if big? 12 6)
                         :background-color (if dark? f-colors/neutral-90
                                               "white")
                         :border-radius 100
                         :padding 2}}
        [icons/icon (if (= icon :locked)
                      :main-icons/locked
                      :main-icons/unlocked)
         {:color (if dark?
                   f-colors/neutral-40
                   f-colors/neutral-50)
          :width 16
          :height 16}]])]]])

(defn preview-channel-avatar []
  [rn/view
   [channel-avatar {:coordinates {:top 20
                                  :left 50}
                    :dark? false
                    :icon :locked
                    :big? true}]
   [channel-avatar {:coordinates {:top 40
                                  :left 100}
                    :dark? false
                    :icon :unlocked
                    :big? false}]])