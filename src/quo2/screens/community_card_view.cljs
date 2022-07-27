(ns quo2.screens.community-card-view
  (:require [quo.react-native :as rn]
            [quo.previews.preview :as preview]
            [reagent.core :as reagent]
            [status-im.constants :as constants]
            [quo.design-system.colors :as quo.colors]
            [quo2.foundations.colors :as colors]
            [quo2.components.community-card-view :as community-view]
            [status-im.react-native.resources :as resources]))

(def view-style (reagent/atom :card-view))

(def community-data
  {:data [{:id             constants/status-community-id
           :name           "Status"
           :description    "Status is a secure messaging app, crypto wallet and web3 browser built with the state of the art technology"
           :status         "gated"
           :section        "popular"
           :permissions    true
           :cover          (resources/get-image :community-cover-image)
           :community-icon (resources/get-image :status-logo)
           :color          (rand-nth quo.colors/chat-colors)
           :token-groups   [{:id  1 :tokens [{:id 1 :token-icon (resources/get-image :status-logo)}]}]
           :tags [{:id 1 :label "Crypto" :emoji (resources/reactions :angry)}
                  {:id 2 :label "NFT"    :emoji (resources/reactions :love)}
                  {:id 3 :label "DeFi"   :emoji (resources/reactions :thumbs-up)}]}
          {:id             2
           :name           "Politics"
           :description    "Status is a secure messaging app, crypto wallet and web3 browser built with the state of the art technology"
           :status         "gated"
           :section        "popular"
           :permissions    true
           :community-icon (resources/get-image :status-logo)
           :color          (rand-nth quo.colors/chat-colors)
           :token-groups   [{:tokens [{:id 1 :token-icon (resources/get-image :status-logo)}]}]
           :tags [{:id 1 :label "Crypto" :emoji (resources/reactions :angry)}
                  {:id 2 :label "NFT"    :emoji (resources/reactions :love)}
                  {:id 3 :label "DeFi"   :emoji (resources/reactions :thumbs-up)}]}]})

(def view-style-descriptor [{:label   "Community views"
                             :key     :card-view
                             :type    :select
                             :options [{:key   :card-view
                                        :value "Card view"}
                                       {:key   :list-view
                                        :value "List view"}]}])

(defn cool-preview []
  (let [view-style (reagent/atom {:value :card-view})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer view-style view-style-descriptor]])))

(defn render-fn [community-item]
  (if (= @view-style :card-view)
    [community-view/community-card-view-item community-item]
    [community-view/communities-list-view-item community-item]))

(defn community-list-key-fn [item]
  (:id item))

(defn get-item-layout [_ index]
  #js {:length 64 :offset (* 64 index) :index index})

(defn preview-community-card []
  (let [items (get community-data :data)]
    [rn/view {:background-color (colors/theme-colors
                                 colors/neutral-5
                                 colors/neutral-95)
              :flex             1
              :padding-left     20}
     [rn/flat-list {:header                            [cool-preview]
                    :key-fn                            community-list-key-fn
                    :getItemLayout                     get-item-layout
                    :keyboard-should-persist-taps      :always
                    :data                              items
                    :render-fn                         render-fn}]]))
