(ns todo.test.db.core
  (:require [todo.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [todo.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'todo.config/env
      #'todo.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-to_dos
  (jdbc/with-db-transaction [t-conn *db*]
    
    (is (= 0 (count (db/get-to_dos))))
    (is (= 1 (db/create-to_do
               {
                :description "Clean Bathroom"
                :completed false })))
    (is (= 1 (db/update-to_do{:id 1 :description "Clean Bathroom 1" :completed true}))
            )))
