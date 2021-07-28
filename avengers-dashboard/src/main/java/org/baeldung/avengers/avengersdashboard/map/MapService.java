package org.baeldung.avengers.avengersdashboard.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.baeldung.avengers.avengersdashboard.astra.CqlClient;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Instant;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MapService {
  @Autowired
  private CqlClient cqlClient;

  public List<String> listAvengers() {
    var rows = cqlClient.query("select distinct avenger from avengers.events");

    return rows.stream()
      .map(row -> row.getString("avenger"))
      .sorted()
      .collect(Collectors.toList());
  }

  public Map<String, List<Location>> getPaths(List<String> avengers, Instant start, Instant end) {
    var rows = cqlClient.query("select avenger, timestamp, latitude, longitude, status from avengers.events where avenger in ? and timestamp >= ? and timestamp <= ?", 
      avengers, start, end);

    var result = rows.stream()
      .map(row -> new Location(
        row.getString("avenger"), 
        row.getInstant("timestamp"), 
        row.getBigDecimal("latitude"), 
        row.getBigDecimal("longitude"),
        row.getBigDecimal("status")))
      .collect(Collectors.groupingBy(Location::avenger));

    for (var locations : result.values()) {
      Collections.sort(locations, Comparator.comparing(Location::timestamp));
    }

    return result;
  }
}
