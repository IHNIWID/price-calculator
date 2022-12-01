# price-calculator

### Assignement rules/description 

You’re implementing a part of a shopping platform. Design and implement a service that will provide a REST API for calculating a price for a given product and its amount. Products in the system are identified by UUID. There should be the possibility of applying discounts based on two policies – amount based (the more pieces of the product are ordered, the bigger the discount is) and percentage based. Policies should be configurable.

#### Additional remarks:

* Use Java >= 8 and frameworks of your choice
*	Project should be containerized, and it should be easy to build and run it via gradle or maven. Please provide README file with instructions on how to launch it
*	There's no need for full test coverage. Implement only most essential (in your opinion) tests
*	Use git repository for developing the project and after you’re done, send us link to it
*	Make sure we can run the project easily, without any unnecessary local dependencies (eg. Don’t use OS specific code)
*	Try not to spend more than one or two evening on the assignment
*	You will eventually have a chance to explain your code on next stage of the interview

### How to run

* Java version used 17
* In terminal `./gradlew clean build` then `./gradlew bootRun` and that's it.
* Application will run at port: `8080`, swagger is under `http://localhost:8080/swagger-ui/index.html`.
* Database is in memory h2, so each time app will be ran it will start from initial state (liquibase migration scripts will be executed).

#### Docker way
* In terminal `docker build -t app/price-calculator-docker .`
* Then `docker run -p 8080:8080 app/price-calculator-docker`
* Application will run at port: `8080`, swagger is under `http://localhost:8080/swagger-ui/index.html`.