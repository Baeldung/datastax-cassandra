package org.baeldung.springcassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.springcassandra.model.Person;
import org.baeldung.springcassandra.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = PersonRepositoryIntegrationTest.Initializer.class)
@EnableConfigurationProperties
public class PersonRepositoryIntegrationTest {

    @Container
    public static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:3.11.2")
      .withExposedPorts(9042)
      .withStartupTimeout(Duration.ofMinutes(2));;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            cassandra.start();
            createKeyspace(cassandra.getCluster());

            TestPropertyValues.of(
              "org.springframework.data.cassandra.core.cql.CqlTemplate=DEBUG",
              "spring.data.cassandra.schema-action=create_if_not_exists",
              "spring.data.cassandra.keyspace-name=test",
              "spring.data.cassandra.local-datacenter=datacenter1",
              "spring.data.cassandra.contact-points=" + cassandra.getContainerIpAddress(),
              "spring.data.cassandra.port=" + cassandra.getMappedPort(9042)
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    private static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS test WITH replication = \n" +
              "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenCassandraStarted_thenCassandraIsRunning() {
        Assertions.assertTrue(cassandra.isRunning());
    }

    @Test
    public void whenSavingPerson_thenOk() {
        UUID personId = UUIDs.timeBased();
        final Person newPerson = Person.builder()
          .id(personId)
          .first("luka")
          .last("modric")
          .birthDate1(LocalDate.now())
          .birthDate2(LocalDateTime.now())
          .birthDate3(new Date())
          .build();
        personRepository.save(newPerson);

        List<Person> savedPerson = personRepository.findAllById(List.of(personId));
        Assertions.assertEquals(newPerson.getFirst(), savedPerson.get(0).getFirst());
    }


}
