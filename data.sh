#!/bin/bash

source ./application.properties

# Data for the statuses collection
curl -v --request PUT --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/namespaces/$ASTRA_DB_KEYSPACE/collections/statuses/latest \
  -H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{
    "captain_america": {"name": "Captain America", "realName": "Steve Rogers", "status": "RETIRED"}, 
    "iron_man": {"name": "Iron Man", "realName": "Tony Stark", "status": "DECEASED"}, 
    "thor": {"name": "Thor", "status": "UNKNOWN"}, 
    "hulk": {"name": "Hulk", "realName": "Bruce Banner", "status": "HEALTHY", "location": "New York"}, 
    "hawkeye": {"name": "Hawkeye", "realName": "Clint Barton", "status": "INJURED", "location": "New York"}, 
    "wanda": {"name": "Wanda Maximoff", "status": "HEALTHY", "location": "New York"}, 
    "falcon": {"name": "Falcon", "realName": "Sam Wilson", "status": "INJURED", "location": "New York"}, 
    "black_widow": {"name": "Black Widow", "realName": "Natasha Romanov", "status": "DECEASED"}
  }'

# Schema for the events collection
curl -v --request DELETE --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/schemas/keyspaces/$ASTRA_DB_KEYSPACE/tables/events \
-H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" \

curl -v --request POST --url https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/schemas/keyspaces/$ASTRA_DB_KEYSPACE/tables \
-H "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
	"name": "events",
	"columnDefinitions":
	  [
      {
	      "name": "avenger",
	      "typeDefinition": "text"
	    },
      {
	      "name": "timestamp",
	      "typeDefinition": "timestamp"
	    },
      {
	      "name": "latitude",
	      "typeDefinition": "decimal"
	    },
      {
	      "name": "longitude",
	      "typeDefinition": "decimal"
	    },
      {
	      "name": "status",
	      "typeDefinition": "decimal"
	    }
	  ],
	"primaryKey":
	  {
	    "partitionKey": ["avenger"],
      "clusteringKey": ["timestamp"]
	  }
}'

# Data for the events collection
lat_start=40.714558
lng_start=-73.975029

for avenger in hulk hawkeye wanda falcon; do
    timestamp=1621155600 # Monday, 16 May 2021 09:00:00 GMT 
    lat=$lat_start
    lng=$lng_start
    status=1.00

    while [ $timestamp -le 1621242000 ]; do # Monday, 17 May 2021 09:00:00 GMT
        timestamp=$(( timestamp + 30 ))
        time=`date -r ${timestamp} -u +"%Y-%m-%dT%H:%M:%SZ"`
        lat=`echo "((($RANDOM % 2000) - 1000) * 0.000001) + $lat" | bc`
        lng=`echo "((($RANDOM % 2000) - 1000) * 0.000001) + $lng" | bc`
        status=`echo "$status - (($RANDOM % 100) * 0.000002)" | bc | awk '{printf "%f", $0}'`

        curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data "{\"avenger\": \"$avenger\",\"timestamp\": \"$time\",\"latitude\":$lat,\"longitude\":$lng,\"status\":$status}"
    done
done
