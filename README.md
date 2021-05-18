# fetch-rewards-service
This is a SpringBoot REST API service that would take in a specific input JSON and make an API call to the respective 
service endpoints that add the payer's points, returns them and updates the points after the customer has spent them.

## To Build and contribute to the project you may install VS Code or IntelliJ:
```Install InteeliJ for developemnt and running the project 
https://code.visualstudio.com/download
https://www.jetbrains.com/help/idea/installation-guide.html
```


## To Run the project you must have the below prerequisites:
```
Install Java --> https://openjdk.java.net/projects/jdk/14/
Install Maven --> https://maven.apache.org/install.html

Some Helpful Links-->
http://maven.apache.org/download.cgi


```

## To build:

```
mvn clean install -U
```

## To Run the project

### Step 1. Make Sure to set the environment variable as it is configurable for Spring. I have set it currently as prod(by default), but it can be configured and overridden by setting the environment variable as 
`spring.profiles.active=dev`

`spring.profiles.active=stage`

`spring.profiles.active=prod`

### Step 2. Copy the input.csv file to `src/main/java/com/fetchrewards/fetchrewardsservice/inputs` location.

### Step 3. Run the Project
There are two ways by which we can run the project:- 
 
1. The Maven Run way
```
mvn spring-boot:run
```
Profiles Active : dev,stage,prod

2. The Portable Java way (Plug and Play Fat Jar)  `java -jar target/fetch-rewards-service-0.0.1-SNAPSHOT.jar ` 

### Step 4. After you have run the Project, you can hit the service via POSTMAN 

### To Install POSTMAN goto below link

```
https://www.postman.com/downloads/

```

## API End Points of Service

## Cumulative Health Check of the Service 
```http://localhost:8080/actuator/health```
### Health Check Sample response
```aidl 
{
"status": "UP",
"components": {
"db": {
"status": "UP",
"details": {
"database": "H2",
"validationQuery": "isValid()"
}
},
"diskSpace": {
"status": "UP",
"details": {
"total": 495684939776,
"free": 333608984576,
"threshold": 10485760,
"exists": true
}
},
"fetchRewardsServiceHealth": {
"status": "UP"
},
"ping": {
"status": "UP"
}
}
}
```

## Get all Payer Points
```http://localhost:8080/fetch-rewards-service/v1/payer/points```

### Health Check Sample Response
```aidl 
{
"status": "UP",
"components": {
"db": {
"status": "UP",
"details": {
"database": "H2",
"validationQuery": "isValid()"
}
},
"diskSpace": {
"status": "UP",
"details": {
"total": 495684939776,
"free": 333608984576,
"threshold": 10485760,
"exists": true
}
},
"fetchRewardsServiceHealth": {
"status": "UP"
},
"ping": {
"status": "UP"
}
}
}
```

## Add Points for specific Payer 
```http://localhost:8080/fetch-rewards-service/v1/payer/addpoints```
### Sample Request to add points 
```aidl
{
    "payer": "DANNON",
    "points": 300,
    "timestamp": "2020-10-31T10:00:00Z"
}
```
## Spend Points for a customer 
```http://localhost:8080/fetch-rewards-service/v1/customer/spendpoints```
### Sample Request to Spend points by a customer
```aidl
{
    "points": 5000
}
```

## curl commands to hit the service alternatively

```aidl
curl -d  '{ "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }' -H 'Content-Type: application/json' http://localhost:8080/fetch-rewards-service/v1/payer/addpoints

curl -d  '{ "points": 5000 }' -H 'Content-Type: application/json' http://localhost:8080/fetch-rewards-service/v1/customer/spendpoints

curl http://localhost:8080/fetch-rewards-service/v1/payer/points

curl http://localhost:8080/actuator/health

```

## Configurations
The endpoint, hostname and the external service being called are all configurable at `src/main/resources/application.yml`. The active profiles can also be chosen at run time by setting the environment variable as shown above

## Logging and Other Metrics
These metrics have been defined at `src/main/resources/application.properties` and can be configured there as per the requirement and need.
