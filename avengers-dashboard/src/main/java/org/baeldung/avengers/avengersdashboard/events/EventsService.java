package org.baeldung.avengers.avengersdashboard.events;

import java.time.Instant;
import org.baeldung.avengers.avengersdashboard.astra.GraphqlClient;
import org.baeldung.avengers.avengersdashboard.astra.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {
  @Autowired
  private RestClient restClient;

  @Autowired
  private GraphqlClient graphqlClient;

  public void createEvent(String avenger, Double latitude, Double longitude, Double status) {
    var event = new Event(avenger, Instant.now().toString(), latitude, longitude, status);

    restClient.createRecord("events", event);
  }

  public Events getEvents(String avenger, String offset) {
    var query = "query {" + 
      "  events(filter:{avenger:{eq:\"%s\"}}, orderBy:[timestamp_DESC], options:{pageSize:5, pageState:%s}) {" +
      "    pageState " +
      "    values {" +
      "     timestamp " +
      "     latitude " +
      "     longitude " +
      "     status" +
      "   }" +
      "  }" +
      "}";

    var fullQuery = String.format(query, avenger, offset == null ? "null" : "\"" + offset + "\"");

    return graphqlClient.query(fullQuery, EventsGraphqlResponse.class).data().events();
  }

  private static record EventsResponse(Events events) {}
  private static record EventsGraphqlResponse(EventsResponse data) {}
}