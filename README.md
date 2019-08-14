# Automaton Template

Template project for automaton based projects. You can use this project as template to create your own 
full-stack GraphQL application.

## Getting started

Use the github template functionality or fork this project otherwise.

The project uses a maven directory structure for both client and server-side code.

# Maven Build

The Java / server-side build of the project is done with maven (See `pom.xml`) 


```shell script 
mvn install
```

Runs the complete Java and JavaScript production build and puts everything into a WAR archive.

The project is prepared to be imported into IntelliJ. A server named "Local Tomcat" is set up
that deploys an "exploded WAR" of the current sources.

A set up like this allows the webpack build products to be directly deployed into the exploded
war folder during development, which at least updates the application scripts and injections etc
without restart or redeployment (no JavaScript hot-reload for now).

# Webpack Build

The webpack build is done via package.json scripts. The project uses yarn as NPM dependency handler.


```shell script 
yarn run watch
```

To start the watch-mode which continuously transpiles changed JavaScript sources and copies it to the 
exploded-WAR folder.


```shell script 
yarn test
```

to run the JavaScript tests in `src/test/js/`

## Java Packages

| Package                                          | Description |
|--------------------------------------------------|-------------|
|de.quinscape.automatontemplate.domain| generated JOOQ classes (for the example database) 
|de.quinscape.automatontemplate.model | Java-based Domain Types (empty) 
|de.quinscape.automatontemplate.runtime.config      | Spring boot configuration
|de.quinscape.automatontemplate.runtime.controller  | WebMVC controllers ( component-scanned) 
|de.quinscape.automatontemplate.runtime.service     | @GraphQLLogic / general services ( component-scanned)
 
 
## Spring Boot Configuration

The main class `de.quinscape.automatontemplate.runtime.AutomatonTemplateApplication` imports several @Configuration 
files from the `de.quinscape.automatontemplate.runtime.config` package that define the actual spring beans 
forming the application infrastructure.

| Configuration File             | Description 
|--------------------------------|-------------
| DataSourceConfiguration        | Sets up an Atomikos-JTA data-source, currently configured to Postgres via properties.   
| GraphQLConfiguration           | Contains the configuration for the DomainQL/GraphQL schema connecting the database world and GraphQL
| MethodSecurityConfiguration    | Prepared configuration for Spring method security, disabled by default.
| SecurityConfiguration          | Spring Security configuration (by default requiring ROLE_USER for application URIs and ROLE_ADMIN within the (empty) /admin/** path)
| ServiceConfiguration           | General application services (contains the chat implementation)
| WebConfiguration               | Spring WebMVC configuration with JsViewResolver

In addition to these imports there are two packages configured to be component-scanned 
( `de.quinscape.automatontemplate.runtime.controller` and `de.quinscape.automatontemplate.runtime.service` ).

Put services into either the ServiceConfiguration class or use @Component inside those two packages to auto-detection
of components.

Note that @GraphQLLogic is a spring meta annotation and thus also automatically defines spring beans in these
packages.
 
                     
## Database

The project is configured to run off a local PostgreSQL database named "automatontemplate", accessed by a
user named "automatontemplate" with the password "automatontemplate".

The `automatontemplate.backup` files contains a custom-format database dump like this.

There is one user defined called "admin" with password "admin".

## Example App

The project contains one app called "myapp" with a default process "myapp" that implements
a simple CRUD cycle based on an example domain type "Foo"

```graphql
"Example domain type for automatontemplate"
type Foo {
    "DB column 'created'"
    created: Timestamp!
    "DB column 'description'"
    description: String
    "DB column 'flag'"
    flag: Boolean!
    "DB column 'id'"
    id: String!
    "DB column 'name'"
    name: String!
    "DB column 'num'"
    num: Int!
    "Target of 'owner_id'"
    owner: AppUser!
    "DB foreign key column 'type'"
    type: String!
}

"Catalog-like domain object, referenced by Foo."
type FooType {
    "DB column 'name'"
    name: String!
    "DB column 'ordinal'"
    ordinal: Int!
}

```
                                           

## Contents

```
src/main/java -  Java source packages    

src/main/resources/application.properties            - Spring boot config file
src/main/resources/automatontemplate-dev.properties  - Development properties (database)
src/main/resources/automatontemplate-prod.properties - Production properties (database, empty)
src/main/resources/domain-typedocs.json              - Documentation data for DB-based domain types
src/main/resources/favicon.ico                       - favicon
src/main/resources/source-typedocs.json              - Documentation data extracted from Javadocs

src/main/js                       -  Js source modules
src/main/js/components/Layout.js  -  Default Layout component for the application
src/main/js/login.js              -  Login form end-point
src/main/js/apps/                 -  Application modules in automaton standard format
src/main/js/apps/myapp            -  Example app with CRUD process
 
src/main/webapp           - Oldschool webapp folder (to enable "exploded WAR" dev mode)
src/main/webapp/css       - Fontawesome icons and automaton Bootstrap 4 theme 
src/main/webapp/js        - For third-party js (empty)
src/main/webapp/media     - Media files (empty)             
src/main/webapp/webfonts  - Contains the font for the automaton default theme             
src/main/webapp/index.jsp - Redirects to the default application              

src/main/webapp/WEB-INF/automaton       - Current automaton models (emtpy) 
                                          run "yarn run js-to-model" to update from src/main/js/apps 
src/main/webapp/WEB-INF/template.html   - Mini spring-jsview HTML template (everything happens in react) 

automatontemplate.backup - custom-format backup of the example database 
```

## Java Tests
  
|Source|Description|
|---|---|
|AutomatonTemplateApplicationTests|Example for an service test that includes some of the application configuration| 
|SeleniumTestITCase|Example for an integration test 



## Js Tests
  
|Source|Description|
|---|---|
|src/test/js/test.js|Dummy test ensuring power-asserts work 
|src/test/resources/logback-test.xml|Log level configuration for tests 



