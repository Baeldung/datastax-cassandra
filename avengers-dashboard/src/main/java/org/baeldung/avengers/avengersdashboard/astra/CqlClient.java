package org.baeldung.avengers.avengersdashboard.astra;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CqlClient {
  @Value("${ASTRA_DB_CLIENT_ID}")
  private String clientId;

  @Value("${ASTRA_DB_CLIENT_SECRET}")
  private String clientSecret;

  public List<Row> query(String cql, Object... binds) {
    try (CqlSession session = connect()) {
      var statement = session.prepare(cql);
      var bound = statement.bind(binds);
      var rs = session.execute(bound);

      return rs.all();
    }
  }

  private CqlSession connect() {
    return CqlSession.builder()
    .withCloudSecureConnectBundle(CqlClient.class.getResourceAsStream("/secure-connect-baeldung-avengers.zip"))
    .withAuthCredentials(clientId, clientSecret)
    .build();
  }
}
