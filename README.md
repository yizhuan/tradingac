# tradingac

Rules: 
	You can only sell existing shares.
	
Seq:

1. load account overview
	

	
	


This application uses:

- Domain Driven Design
- CQRS
- Event Sourcing
- Spring Boot
- Axon Framework
- RESTful service
- MongoDB

# Build

$ mvn package

# Start service

java -jar target/tradingac-0.2.0.jar


# RESTful services

## Traders
curl -X POST -d '{"name":"John Smith"}' -H "Content-Type:application/json" http://localhost:8080/api/traders

curl http://localhost:8080/api/traders

curl http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3

## Trades

curl -X POST -d '{"symbol":"GOOG", "shares": 100,"price":560.9, "type": 1}' -H "Content-Type:application/json" http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/buy

curl -X POST -d '{"symbol":"GOOG", "shares": 100,"price":560.9, "type": 0}' -H "Content-Type:application/json" http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/sell

curl http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/trades

### Trades e.g. Facebook
curl http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/trades/FB

curl http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/trades/FB/1

## Balance
curl http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/balance

### Balance e.g. Facebook
curl http://localhost:8080/api/traders/87b5dc8e-b95a-42d8-93d7-eeb7773195a3/balance/FB

# Check MongoDB

mongo
>db
>show dbs
>use test
>show collections
>db.user.find()


$ curl -X POST -d '{"symbol":"FB", "shares": 20,"price":93.6, "type": 1}' -H "Content-Type:application/json" http://localhost:8080/api/trades

# Check MongoDB

mongo
>show dbs
> use axonframework
switched to db axonframework

> show collections
domainevents
system.indexes

> db.domainevents.find()

>use test

>show collections
system.indexes
tradeEntry

> db.tradeEntry.find()



# TODO

This version is just a rough sketch. Still lots to do:

- Add an interface to connect to real trading system
- Add tests, cover basic tests yet.
- Add Javadocs
- Add a web UI
- Fix TODOs





