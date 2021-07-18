package org.baeldung.springcassandra;

import org.baeldung.springcassandra.config.CassandraContainerInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = SpringCassandraApplicationTest.Initializer.class)
@EnableConfigurationProperties
class SpringCassandraApplicationTest extends CassandraContainerInitializer {

    @Test
    void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
        assertThat(cassandra.isRunning()).isTrue();
    }

}