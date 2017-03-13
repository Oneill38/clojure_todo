(ns todo.routes.home
  (:require [todo.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "home.html"))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/about" []
       (-> (response/ok (-> "public/about.html" io/resource slurp))
       (response/header "Content-Type" "text/html; charset=utf-8"))))

