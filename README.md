# Transaction Processor

Simple Java / Maven project using Spring Boot.

## Overview
- Java: 17
- Build: Maven
- Spring Boot parent: `3.5.11` (use Spring Boot starters for dependency management)

## Project coordinates
- GroupId: `org.example`
- ArtifactId: `transaction-processor`
- Version: `1.0-SNAPSHOT`

## Prerequisites
- Java 17 SDK installed
- Maven 3.8+
- (Optional) IntelliJ IDEA for development

## Build
`mvn clean package`

## Run
`mvn spring-boot:run` or `java -jar target/transaction-processor-1.0-SNAPSHOT.jar`

## Tests
`mvn test`


## Edge Cases

## Terminology
CH -> Checking Account, 
SV -> Savings Account, 
DB -> Debit, 
CR -> Credit, 
WD -> Withdrawal

## Given more time, I thought of adding more features like below:
-> Add proper logging using SLF4J and Logback
-> Implement more comprehensive unit and integration tests using JUnit and Mockito
-> Add more validations for account creation and transactions
-> Add more exception handling for various error scenarios
-> Add account ledger to track all transactions for each account
-> Implementing Withdrawal flow

## Updated curl's for testing through Postman
<!-- Use these curl commands in Postman to test the API endpoints. -->
##---------------------------------------------------

To Test acctId == null to get 400 Bad Request error.

curl -i -X POST http://localhost:8080/api/v1/tps/app/accounts \
-H "Content-Type: application/json" \
-d '{
"acctType": "SV",
"acctCurrencyType": "USD",
"acctHolderFn": "John",
"acctHolderLn": "Doe"
}'

Response :

{
"timestamp": "2026-05-21T18:58:45.418+00:00",
"status": 400,
"error": "Bad Request",
"path": "/api/v1/tps/app/accounts"
}


---------------------------------------------------

To Test a happy scenario :: to create a new account with all required fields

curl -i -X POST http://localhost:8080/api/v1/tps/app/accounts \
-H "Content-Type: application/json" \
-d '{
"acctId": 1005,
"acctType": "CH",
"acctCurrencyType": "INR",
"acctHolderFn": "Lisa",
"acctHolderLn": "Anakova",
"acctCreatedTime": "2026-05-21T12:34:56",
"acctUpdatedtedTime": "2026-05-21T12:34:56",
"acctDesc" : "Checking account",
"acctBalance" : 2100.00
}'

---------------------------------------------------

