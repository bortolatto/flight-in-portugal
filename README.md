# FIP - Flight in Portugal

This API is responsible for getting the average prices of flights through the public API http://api.skypicker.com. Besides that, it's possible to get the average price for one or two baggage. As a company of Portugal, the first version only accepts queries for two airlines: TAP Portugal and Ryanair, and only LIS and OPO source/destinations airports.

Example of how we can consume the API:
http://localhost:8080/flight/avg?date_from=01/08/2020&partner=TAP&source_airport=LIS&target_airport=OPO

And the response:
```
{ 
   "priceAverage":"64.36",
   "bagsAverage":{ 
      "oneBagagge":"37.82",
      "twoBaggage":"93.93"
   
},
   "dateFrom":"01/08/2020",
   "dateTo":"",
   "sourceAirport":"LIS",
   "targetAirport":"OPO",
   "partner":"TAP",
   "currency":"EUR"
}
```

The API returns well-formatted messages to provide more understanding about errors. For example, if the client did not give a required parameter, the possible response may be:

```{ 
   "status":"BAD_REQUEST",
   "timestamp":"29-01-2020 02:28:14",
   "message":"Required String parameter 'target_airport' is not present",
   "debugMessage":null,
   "subErrors":[ 
      { 
         "object":"target_airport",
         "field":null,
         "rejectedValue":null,
         "message":"Required String parameter 'target_airport' is not present"
}
]
}
```

**For more information about the query parameters, please go to http://localhost:8080/swagger-ui.html.**

# Business rules
1.  Source and target airports must be not equals;
2.  If the client does not provide 'date_to' parameter, the API will find all the flights starting in 'date_from' value.
3.  The only two partners accepted are TAP or RYR.
4.  Default currency is EUR.


# Setup the Application
They're two different approaches for getting the application to run:

*  Using a docker image;

*  Using a maven wrapper.

The easiest way is getting the public docker image available on Docker Hub: bortolattol/fip:0.0.1-SNAPSHOT using the following commands:

```
docker pull bortolattol/fip:0.0.1-SNAPSHOT
docker run -p 8080:8080 bortolattol/fip:0.0.1-SNAPSHOT
```

The second approach is to run through a maven wrapper with the following command:
```
./mvnw spring-boot:run
```

# Additional endpoints
The API provides two additional endpoints:
* /all-requests: shows the historic of all requests
* /erase: remove the entire history of the requests