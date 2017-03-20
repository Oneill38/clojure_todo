# todo

Disclaimer: This is my first time using Clojure/everything included in this stack.

This to-do application was build using the Luminus Web Framework. From poking around a bit, it seems to be similar in ways to Rails (i.e. convention over configuration). It comes complete with a small testing suite, and the ability to add a to-do, see all to-dos, and update a to-do as "completed".

As compared to other languages/frameworks, I definitely thinks there's less literature and examples available.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed, and a MySQL database

## The Stack

* Clojure
* Hiccup
* Leiningen
* Luminus
* Reagent
* Swagger

## Running

Setup a MySQL DB, and put the credentials in profiles.clj. For example:

`{:profiles/dev  {:env {:database-url "mysql://localhost:3306/to_do_app_dev?user=user&password=pass"}}
 :profiles/test {:env {:database-url "mysql://localhost:3306/to_do_app_test?user=user&password=pass"}}}`

To start a web server for the application, run:
	lein figwheel
    lein run

Navigate to localhost:3000 to run the application
To checkout the api, navigate to localhost:3000/swagger-ui

## Next Steps

* Add in Clojurescript testing
* Allow users to un-check a to-do item, to say it is no longer completed
* Add delete functionality

