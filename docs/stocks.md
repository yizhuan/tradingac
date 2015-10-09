FORMAT: 1A
HOST: https://qubits.mobi

# Stock Service API

# Group Stock Resource

## Stock List [/api/stocks]

### Get Stocks [GET]
Get all stocks as a list 

+ Response 200

    + Body 

            [ 
              {
	              	"symbol":"",	
					"name:"",
					"open:"",
					"prevClose:"",
					"currentQuote:"",
					"high:"",
					"low:"",			
					"date:"",
					"quoteTime:"",						
					"timestamp:""
              },
              ...
            ]

### Create Stock [POST]

+ Request

    + Body

            {
              "symbol":""
            }

+ Response 201
    
    + Headers

            Location: http://../api/stocks/{id}

## Order [/api/stocks/{id}]

+ Parameters

    + id (required, string, `68a5sdf67`) ... The Stock ID

+ Model

    + Body

            {
            		"id":"",
	              	"symbol":"",	
					"name:"",
					"open:"",
					"prevClose:"",
					"currentQuote:"",
					"high:"",
					"low:"",			
					"date:"",
					"quoteTime:"",						
					"timestamp:""
            }
 
### Get Stock [GET]
Get the information of a single stock. 

+ Response 200

    [Stock][]

### Change Stock [PUT]

+ Response 200

### Remove Stock [DELETE]

+ Response 200

