package org.baeldung.springcassandra.repository;

import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.springcassandra.config.CassandraContainerInitializer;
import org.baeldung.springcassandra.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = PersonRepositoryIntegrationTest.Initializer.class)
@EnableConfigurationProperties
public class PersonRepositoryIntegrationTest extends CassandraContainerInitializer {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void givenValidPersonRecord_whenSavingIt_thenDataIsPersisted() {
        UUID personId = UUIDs.timeBased();
        final Person newPerson = Person.builder()
          .id(personId)
          .firstName("Luka")
          .lastName("Modric")
          .build();
        personRepository.save(newPerson);

        List<Person> savedPersons = personRepository.findAllById(List.of(personId));
        assertThat(savedPersons.get(0)).isEqualTo(newPerson);
    }

    @Test
    public void givenValidPersonUsingLocalDate_whenSavingIt_thenDataIsPersisted() {
        UUID personId = UUIDs.timeBased();
        final Person newPerson = Person.builder()
          .id(personId)
          .firstName("Luka")
          .lastName("Modric")
          .birthDate(LocalDate.of(1985, 9, 9))
          .build();
        personRepository.save(newPerson);

        List<Person> savedPersons = personRepository.findAllById(List.of(personId));
        assertThat(savedPersons.get(0)).isEqualTo(newPerson);
    }

    @Test
    public void givenValidPersonUsingLocalDateTime_whenSavingIt_thenDataIsPersisted() {
        UUID personId = UUIDs.timeBased();
        final Person newPerson = Person.builder()
          .id(personId)
          .firstName("Luka")
          .lastName("Modric")
          .lastVisitedDate(LocalDateTime.of(2021, 7, 13, 11, 30))
          .build();
        personRepository.save(newPerson);

        List<Person> savedPersons = personRepository.findAllById(List.of(personId));
        assertThat(savedPersons.get(0)).isEqualTo(newPerson);
    }

    @Test
    public void givenValidPersonUsingLegacyDate_whenSavingIt_thenDataIsPersisted() {
        UUID personId = UUIDs.timeBased();
        final Person newPerson = Person.builder()
          .id(personId)
          .firstName("Luka")
          .lastName("Modric")
          .lastPurchasedDate(new Date(LocalDate.of(2021, 7, 13).toEpochDay()))
          .build();
        personRepository.save(newPerson);

        List<Person> savedPersons = personRepository.findAllById(List.of(personId));
        assertThat(savedPersons.get(0)).isEqualTo(newPerson);
    }

}
