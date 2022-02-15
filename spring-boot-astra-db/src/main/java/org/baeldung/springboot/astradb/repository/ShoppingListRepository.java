package org.baeldung.springboot.astradb.repository;

import org.baeldung.springboot.astradb.entity.ShoppingList;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends CassandraRepository<ShoppingList, String> {

    ShoppingList findByTitleAllIgnoreCase(String title);

}