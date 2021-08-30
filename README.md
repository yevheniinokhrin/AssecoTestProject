
# Asseco Data Systems Test Project


This project was created as a test task for Asseco DB company. It is based on simple business concept: registration of new employees, districts and positions.

## Requirements


* Java 1.8;
* Maven (for building);
* Relational Database.

## Configuration Instructions


To connect your database to this application, set necessary parameters in hibernate.properties file. The path to this file is `\src\main\resources`. 
Give your database url, login and password. This application used a PostgreSQL database for data storage, but you can set yours by changing a `hibernate.dialect` property. Full list of avaliable dialects you can find [here](https://www.javatpoint.com/dialects-in-hibernate).

## Build Instructions

To build the project, run the command below.

```
mvn package
```

This command will build the project and package it into an executable, shaded JAR. A shaded JAR file contains all of the project's dependencies. 

## Deploy Instructions

* Use command `cd target` to change directory;
* Run application using command `java -jar target/OakBot-VERSION.jar`.


## Usage instructions


This application contain user interface which allows to add and remove employees using a simple form. Also is possible to create new district and position or get full information about employee in the special dialog window. In addition, there is a functionality, which allow to get average salary for district selected in the table. 

There are many buttons on the form which make using the program very convenient, but some of them, for example, "remove user" or "show average salary" are available only when the corresponding tables are selected.

