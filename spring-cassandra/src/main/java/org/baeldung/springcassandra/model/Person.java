package org.baeldung.springcassandra.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Table
public class Person {

    @PrimaryKey
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private LocalDateTime lastVisitedDate;

    private Date lastPurchasedDate;

}
