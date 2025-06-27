# chronos-couriers-syncron
## Assignment

Hello Team.

Developed a springboot application of chronos-couriers courier Services.

## Application Overview:
The application provides main functionalities. Placing an order, getting status of Package and geting History of all Packages.
The user can place an order using place Order API and in response we can get the The type of rider assigned along with the Rider ID and Package ID Assigned to it.

We can get the status of the Package using the status API and get the status of the Package Ordered.

We can also get the history of order along with their status using history API.

### Constants:
**DeliveryType**: STANDARD,EXPRESS
**PackageType**: NORMAL,SENSITIVE,CLOAKED

## Interacting with the application
Please find the following commands to make Http calls using Curl:

### Placing Order:
**Place Order API**

curl --request POST \
  --url http://localhost:8080/courier \
  --header 'content-type: application/json' \
  --data '{
  "deliveryType": "EXPRESS",
  "packageType": "NORMAL",
  "deadLine": 1722069800000
}'

### Getting status of the package:
**Status API**

 curl http://localhost:8080/courier/status/PKG123
 
### Getting History:
**History API**

curl --request GET \
  --url http://localhost:8080/courier/history \
  --header 'content-type: application/json'

## Application Design:
Java Version: 17
Spring Boot version: 3.5.3
Server: Tomcat
When the application starts, some riders are loaded and to simulate delivering orders, a method is written and scheduled to execute every 5 seconds, which randomly chooses whether a package is to be delivered or keep it Delivering. If no packages are available it will log thet no Packages are available to deliver.

When the user plcaes an Order, an ID is assigned to it and order time is set. Based on the Package type, the riders list is fetched and if no riders is available it will throw Exception.
The status of the rider and Package is changed and IDs get assigned to them. The package, the rider and the assignment - each stored in theri respective repos.

Map is being used to store as repo/cache for faster retrieval of data. Repos get updated with each transaction. 

PriorityQueue is used to store and sort the Packages as per teh priority specified. Implemented compareTo in Package class for the logic which is used to sort the data in the Queue.
The top priority package is dispatched first.

A list of History is maitained which gets updated when an order is placed nad delivered. This list can be fetched through History API.
