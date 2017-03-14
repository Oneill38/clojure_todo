(ns todo.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [todo.db.core :as db]))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  
  (context "/api" []
    :tags ["to_dos"]

    (GET "/to_dos" []
      :return       [{:id Integer :description String :completed Boolean}]
      :summary      "Get all to_dos"
      (ok (db/get-to_dos)))

    (POST "/to_do" request
      :return Long
      :body-params [description :- String]
      :summary "Add new To Do"
      (ok (db/create-to_do {:description description
                            :completed false})))

    (PUT "/to_do/:id" []
      :return Long
      :body-params [id :- s/Int]
      :summary     "Update to-do"
      (ok (db/update-to_do{:id id
                           :completed true}))
      )


      ))
