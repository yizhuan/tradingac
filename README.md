# tradingac

This application uses:

- Domain Driven Design
- CQRS
- Event Sourcing
- Spring Boot
- Axon Framework
- RESTful service

# Build

$ mvn package

# Start service

java -jar target/tradingac-0.1.0.jar


# Use REST services


$ curl http://localhost:8080/users

$ curl -X POST -d '{"firstName":"Pat", "lastName":"Rabbit"}' -H "Content-Type:application/json" http://localhost:8080/users 
$ curl -X POST -d '{"firstName":"Jim", "lastName":"Roggers"}' -H "Content-Type:application/json" http://localhost:8080/users

(TODO: User management and access control) 

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
{ "_id" : ObjectId("55bfbada1543ad1ee2cd3caa"), "aggregateIdentifier" : "0a002ec3-ffcb-4362-adce-c0b881688f66", "sequenceNumber" : NumberLong(0), "serializedPayload" : "<mobi.qubits.tradingac.domain.events.BuyEvent><id>0a002ec3-ffcb-4362-adce-c0b881688f66</id><symbol>FB</symbol><shares>20</shares><price>93.6</price></mobi.qubits.tradingac.domain.events.BuyEvent>", "timeStamp" : "2015-08-03T20:02:49.946+01:00", "type" : "Trade", "payloadType" : "mobi.qubits.tradingac.domain.events.BuyEvent", "payloadRevision" : null, "serializedMetaData" : "<meta-data/>", "eventIdentifier" : "4dd4fba8-2848-4324-8693-cb5c67610a48" }
>


>use test

>show collections
system.indexes
tradeEntry

> db.tradeEntry.find()
{ "_id" : ObjectId("55bfc06f15437e7e64b136d7"), "_class" : "mobi.qubits.tradingac.query.trade.TradeEntry", "eventId" : "072eaed3-5db9-496c-8db2-aac1e1ee2dda", "symbol" : "FB", "shares" : NumberLong(15), "price" : 93.5999984741211, "type" : 1 }
>

List all holdings
$ curl http://localhost:8080/api/tradingaccount

Find Facebook
$ curl http://localhost:8080/api/tradingaccount/FB


# TODO

This version is just a rough sketch. Still lots to do.




