(ns user
  (:require [mount.core :as mount]
            [todo.figwheel :refer [start-fw stop-fw cljs]]
            todo.core))

(defn start []
  (mount/start-without #'todo.core/http-server
                       #'todo.core/repl-server))

(defn stop []
  (mount/stop-except #'todo.core/http-server
                     #'todo.core/repl-server))

(defn restart []
  (stop)
  (start))


