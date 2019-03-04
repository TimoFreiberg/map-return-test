(ns map-return-test.components.ui
  (:require [com.stuartsierra.component :as component]
            [map-return-test.core :refer [render]]))

(defrecord UIComponent []
  component/Lifecycle
  (start [component]
    (render)
    component)
  (stop [component]
    component))

(defn new-ui-component []
  (map->UIComponent {}))
