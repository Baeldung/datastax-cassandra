package org.baeldung.cassandraunit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;

import com.datastax.oss.driver.api.core.cql.ResultSet;

public class JUnitRuleWithEmbeddedCassandraUnitTest {

    @Rule
    public CassandraCQLUnit cassandra = new CassandraCQLUnit(new ClassPathCQLDataSet("people.cql", "people"));

    @Test
    public void givenEmbeddedCassandraInstance_whenStarted_thenQuerySuccess() throws Exception {
        ResultSet result = cassandra.session.execute("select * from person WHERE id=5678");
        assertThat(result.iterator().next().getString("name"), is("Michael"));
    }

}
