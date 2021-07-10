#!/bin/sh

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
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:00:00Z","latitude": 40.727593,"longitude": -73.971798,"status":0.85}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:00:30Z","latitude": 40.726144,"longitude": -73.972157,"status":0.85}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:01:00Z","latitude": 40.724696,"longitude": -73.972516,"status":0.85}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:01:30Z","latitude": 40.723248,"longitude": -73.972875,"status":0.85}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:02:00Z","latitude": 40.721799,"longitude": -73.973234,"status":0.85}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:02:30Z","latitude": 40.720351,"longitude": -73.973593,"status":0.82}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:03:00Z","latitude": 40.718903,"longitude": -73.973952,"status":0.80}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:03:30Z","latitude": 40.717454,"longitude": -73.974311,"status":0.77}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:04:00Z","latitude": 40.716006,"longitude": -73.974670,"status":0.75}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "falcon","timestamp": "2021-05-17T09:04:30Z","latitude": 40.714558,"longitude": -73.975029,"status":0.72}'

curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:00:00Z","latitude": 40.712515,"longitude": -73.968695,"status":1.00}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:00:30Z","latitude": 40.712742,"longitude": -73.969399,"status":0.98}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:01:00Z","latitude": 40.712969,"longitude": -73.970103,"status":0.95}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:01:30Z","latitude": 40.713196,"longitude": -73.970806,"status":0.91}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:02:00Z","latitude": 40.713423,"longitude": -73.971510,"status":0.90}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:02:30Z","latitude": 40.713650,"longitude": -73.972214,"status":0.89}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:03:00Z","latitude": 40.713877,"longitude": -73.972918,"status":0.89}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:03:30Z","latitude": 40.714104,"longitude": -73.973621,"status":0.89}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:04:00Z","latitude": 40.714331,"longitude": -73.974325,"status":0.89}'
curl -s --location --request POST https://$ASTRA_DB_ID-$ASTRA_DB_REGION.apps.astra.datastax.com/api/rest/v2/keyspaces/$ASTRA_DB_KEYSPACE/events --header "X-Cassandra-Token: $ASTRA_DB_APPLICATION_TOKEN" --header 'Content-Type: application/json' --data '{"avenger": "hawkeye","timestamp": "2021-05-17T09:04:30Z","latitude": 40.714558,"longitude": -73.975029,"status":0.89}'

