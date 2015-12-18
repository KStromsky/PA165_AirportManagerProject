# PA165_AirportManagerProject
====================

Project in Java for PA165 - Vývoj programových systémů v jazyce Java


# REST
====================

This is command for main page of REST api:
$ curl -i -X GET http://localhost:8080/AirportREST/

Show list of all airplanes
$ curl -i -X GET http://localhost:8080/AirportREST/airplane

Show airplane with given ID:
$ curl -i -X GET http://localhost:8080/AirportREST/airplane/{$id}

Show airplane with given name:
$ curl -i -X GET http://localhost:8080/AirportREST/airplane/name/{$name}

Command for creation new airplane
$ curl -X POST -i -H "Content-Type: application/json" --data '{"name":"{$NAME}","capacity":"{$CAPACITY}","type":"$TYPE"}' http://localhost:8080/AirportREST/airplane/create