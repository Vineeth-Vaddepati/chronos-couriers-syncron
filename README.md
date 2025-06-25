# chronos-couriers-syncron
Assessment

Hello Team.

Developed a springboot application of chronos-couriers courier Services.

Please find the following commands to make Http calls using Curl:

Placing Order:
curl -X POST http://localhost:8080/courier ^
 -H "Content-Type: application/json" ^
 -d "{ ^
   \"packageId\": \"PKG456\", ^
   \"deliveryType\": \"EXPRESS\", ^
   \"packageType\": \"NORMAL\", ^
   \"deadLine\": 1722069800000^
 }"

Getting status of the package:
 curl http://localhost:8080/courier/status/PKG123


