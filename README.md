# todo

Disclaimer: This is my first time using Clojure/everything included in this stack.



## Prerequisites

You will need [Leiningen][1] 2.0 or above installed, and a MySQL database

## The Stack

* Leiningen
* Luminus
* Reagent
* Swagger

## Running

Setup a MySQL, and put the credentials in project.clj. For example:

`{:profiles/dev  {:env {:database-url "mysql://localhost:3306/to_do_app_dev?user=user&password=pass"}}
 :profiles/test {:env {:database-url "mysql://localhost:3306/to_do_app_test?user=user&password=pass"}}}`

To start a web server for the application, run:

    lein run

## Next Steps

* Complete further testing
* Build out rest of CRUD functionality

