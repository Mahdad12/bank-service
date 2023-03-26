# Getting Started

The small bank project contains of 2 backend services and a frontend:

Table of Contents:

* How To build
  * Account Service
  * Transaction Service
  * Frontend
* How to run
  * Pre-Populated Data  
* Stack
* Next release plan

## How to Build 

#### Account Service (Port 8080)

The **Account Service** is responsible for opening account and getting account info.

To build the project run following docker command from account-service directory:

`docker build -t account-service .`

You can also see **swagger document** in below:

http://localhost:8080/swagger-ui/index.html


#### Transaction Service (Port 8090)

The **Transaction Service** is responsible for create a transaction for an account and getting transactions of an account.

To build the project run following docker command from transaction-service directory:

`docker build -t transaction-service .`

You can see **swagger document** link in below:

http://localhost:8090/swagger-ui/index.html


#### Frontend (Port 8000)

The **Frontend** is responsible for UI part of the application and is consuming different apis.

To build the project run following docker command from frontend directory:

`docker build -t frontend .`


## How to Run

From root directory run following command:

`docker-compose up`

The output can be seen in the following address:

**http://localhost:8000/index.html**

#### Pre-Populated Data

There are some pre-populated data in the Database that you can find here:

Customer: 

ID| Name| Account ID| Balance

1| William Bible| 1| 200.00 (with 3 Transactions)

2| Clayton Cook| 2| 240.00 (with 1 Transactions)

3| Rhonda Granados| 3| 56.00 (with 1 Transactions)

4| Barbara Cortez| No account (No Transaction)

## Stack

* Java 19
* Spring Boot Framework 3.0.5
* H2 in memory database
* Flyway 9.5.1
* Gradle 8.0.2
* Docker
* HTML/CSS/Java Script (Axios)
* CI/CD using github action and docker repository 
  (build and publish images for 3 different services to separate docker repository)
  
## Next release plan
* Adding tests for Frontend.
* Create a service api library and publish the package so each service will use 
  other required service api libraries to avoid creating duplicate classes 
  (now TransactionDto class is duplicated in both services).
* Monitoring open account api usage in order to apply a change on create transaction api and make it asynchronous.