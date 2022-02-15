package org.baeldung.springboot.astradb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.astra.sdk.AstraClient;

@RestController
public class AstraDbApiController {

    @Autowired
    private CassandraTemplate cassandraTemplate;
    
    @Autowired
    private AstraClient astraClient;

    @GetMapping("/ping")
    public String ping() {
        return astraClient.apiDevopsOrganizations()
            .organizationId();
    }

    @GetMapping("/datacenter")
    public String datacenter() {
        return cassandraTemplate
            .getCqlOperations()
            .queryForObject("SELECT data_center FROM system.local", String.class);
    }

}
