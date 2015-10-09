FORMAT: 1A
HOST: https://nsoc.huawei.com

# Order Service API

# Group Order Resource

## Order List [/api/v1/orders]

### Get Orders [GET]
Get all orders as a list 

+ Response 200

    + Body 

            [ 
              {
                "orderId", "order id",
                "user", "user id"
              },
              ...
            ]

### Create Order [POST]

+ Request

    + Body

            {
              "user", "user id",
              "user name", "user name"
            }

+ Response 201
    
    + Headers

            Location: http://../api/v1/orders/{id}

## Order [/api/v1/orders/{id}]

+ Parameters

    + id (required, string, `68a5sdf67`) ... The Order ID

+ Model

    + Body

            {
               "orderId": "order id",
               "orderInfo: "info"
            }
 
### Get Order [GET]
Get a single order information. 

+ Response 200

    [Order][]

### Change Order [PUT]

+ Request

    + Body 

            {
                "orderInfo": "info"
            }

+ Response 200

### Remove Order [DELETE]

+ Response 200

