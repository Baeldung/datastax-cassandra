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
@Table("person")
public class Person {

    @PrimaryKey
    private UUID id;

    private String first;

    private String last;

    private LocalDate birthDate1;

    private LocalDateTime birthDate2;

    private Date birthDate3;

}
