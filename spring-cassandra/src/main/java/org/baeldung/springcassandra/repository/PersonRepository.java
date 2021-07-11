package org.baeldung.springcassandra.repository;

import org.baeldung.springcassandra.model.Person;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CassandraRepository<Person, UUID> {

}
