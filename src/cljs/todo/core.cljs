(ns todo.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [todo.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(defn nav-link [uri title page collapsed?]
  [:li.nav-item
   {:class (when (= page (session/get :page)) "active")}
   [:a.nav-link
    {:href uri
     :on-click #(reset! collapsed? true)} title]])

(defn navbar []
  (let [collapsed? (r/atom true)]
    (fn []
      [:nav.navbar.navbar-dark.bg-primary
       [:button.navbar-toggler.hidden-sm-up
        {:on-click #(swap! collapsed? not)} "☰"]
       [:div.collapse.navbar-toggleable-xs
        (when-not @collapsed? {:class "in"})
        [:a.navbar-brand {:href "#/"} "todo"]
        [:ul.nav.navbar-nav
         [nav-link "#/" "Home" :home collapsed?]
         [nav-link "/about" "About" :about collapsed?]]]])))

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:img {:src (str js/context "/img/warning_clojure.png")}]]]])

(def to_dos (r/atom []))

(GET "/api/to_dos"
  {:handler #(reset! to_dos %)})

(defn update-to_do [id]
  (POST "/api/to_do/:id"
    {:params id }))

(defn to_do-item [{:keys [description completed id]}]
  [:li description " completed: " 
    [:input 
      {:type "checkbox" 
       :on-change #(update-to_do id)}]])

(defn to_do-list [to_dos]
  [:ul
    (for [[idx to_do] (map-indexed vector to_dos)]
      ^{:key idx}
      [to_do-item to_do])]
)

(defn add-to_do [form]
  (POST "/api/to_do"
    {:params @form
     :handler
     #(do
        (swap! to_dos conj @form)
        (reset! form {}))}))

(defn to_do-form []
  (let [form (r/atom {})]
    (fn []
      [:div.form-group
        [:label "Description"]
        [:input.form-control
          {:type :text
           :value (:description @form)
           :on-change
           #(swap! form
                   assoc
                   :description (-> % .-target .-value))
           }]
           [:button.btn.btn-primary
            {:on-click #(add-to_do form)}
            "Add To Do"]])
  ))

(defn home-page []
  [:div.container
   [:h1 "Welcome to To-Do"]
   [:p "Get Started and add some To-Dos"]
   [to_do-form]
   [to_do-list @to_dos]
   ]
   )

(def pages
  {:home #'home-page
   :about #'about-page})

(defn page []
  [(pages (session/get :page))])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :page :home))

(secretary/defroute "/about" []
  (session/put! :page :about))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
              (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(session/put! :docs %)}))

(defn mount-components []
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
