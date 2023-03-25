# Getting Started

The small bank project contains of 2 backend services:

* Account Service: 
  The **Account Service** is responsible for opening account and getting account info.
* Transaction Service:
  The **Transaction Service** is responsible for create a transaction for an account and getting transactions of an account.


## How to Build 

#### Account Service

To build the project run following docker command from account-service directory:

`docker build -t account-service:1.0.1 .`

You can also see **swagger document** in below:

http://localhost:8080/swagger-ui/index.html


#### Transaction Service

The **Transaction Service** is responsible for create a transaction for an account and getting transactions of an account.

To build the project run following docker command from transaction-service directory:

`docker build -t transaction-service:1.0.1 .`

You can see **swagger document** link in below:

http://localhost:8090/swagger-ui/index.html


## How to Run

From root directory run following command:

`docker-compose up`




## Stack

* Java 19
* Spring Boot Framework 3.0.5
* H2 in memory database
* Flyway 9.5.1
* Gradle 8.0.2
* Docker