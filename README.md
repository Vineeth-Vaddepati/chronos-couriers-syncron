# chronos-couriers-syncron
Assessment

Hello Team.

Developed a springboot application of chronos-couriers courier Services.

Please find the following commands to make Http calls using Curl:

Placing Order:
curl -X POST http://localhost:8080/courier ^
 -H "Content-Type: application/json" ^
 -d "{ ^
   \"packageId\": \"PKG123\", ^
   \"deliveryType\": \"EXPRESS\", ^
   \"packageType\": \"NORMAL\", ^
   \"deadLine\": 1722069800000^
 }"

Getting status of the package:
 curl http://localhost:8080/courier/status/PKG123

Constants:
DeliveryType: STANDARD,EXPRESS
PackageType: NORMAL,SENSITIVE,CLOAKED

When Package gets ordered, every five seconds you will get the status of the top priority Package 
