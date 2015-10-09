FORMAT: 1A
HOST: https://qubits.mobi

# Trader Service API

# Group Trader Resource

## Trader List [/api/traders]

### Get Traders [GET]
Get all traders as a list 

+ Response 200

    + Body 

            [ 
              {
              		"id":"514d3101-0852-423d-ac6f-39198279af5c",
	              	"name":"John",	
					"investment:10000.0				
              },
              ...
            ]

### Create Trader [POST]

+ Request

    + Body

            {
                 	"name":"John",	
					"investment: 10000.0
            }

+ Response 201
    
    + Headers

            Location: http://../api/traders/{id}

## Order [/api/traders/{id}]

+ Parameters

    + id (required, string, `514d3101-0852-423d-ac6f-39198279af5c`) ... The Trader ID

+ Model

    + Body

            {
					"id":"514d3101-0852-423d-ac6f-39198279af5c",
	              	"name":"John",	
					"investment:10000.0				
            }
 
### Get Trader [GET]
Get the information of a single stock. 

+ Response 200

    [Trader][]

### Change Trader [PUT]

+ Request

    + Body

            {
                 	"name":"John",	
					"investment: 20000.0
            }
            
+ Response 200

### Remove Trader [DELETE]

+ Response 200

