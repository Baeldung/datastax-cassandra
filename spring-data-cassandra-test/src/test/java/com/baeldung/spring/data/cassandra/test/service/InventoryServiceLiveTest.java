package com.baeldung.spring.data.cassandra.test.service;

import com.baeldung.spring.data.cassandra.test.config.CassandraConfig;
import com.baeldung.spring.data.cassandra.test.domain.Vehicle;
import com.baeldung.spring.data.cassandra.test.repository.InventoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataCassandraTest
@Import(CassandraConfig.class)
public class InventoryServiceLiveTest {
    @Autowired
    private InventoryRepository repository;

    private InventoryService inventoryService;

    public static DockerComposeContainer container =
            new DockerComposeContainer(new File("src/test/resources/compose-test.yml"));

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    public void setUp() {
        inventoryService = new InventoryService(this.repository);
    }

    @Test
    public void givenVehiclesInDBInitially_whenRetrieved_thenReturnAllVehiclesFromDB() {
        List<Vehicle> vehicles = inventoryService.getVehicles();
        assertThat(vehicles).isNotNull();
        assertThat(vehicles).isNotEmpty();
    }

    @Test
    public void whenAddMoreVehiclesToDB_thenRetrievalReturnsAllVehicles() {
        String vin1 = "ABC123";
        String vin2 = "XYZ123";
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(vin1, 2020, "Toyota", "Camry"),
                new Vehicle(vin2, 2019, "Honda", "Prius")
        );
        inventoryService.addVehicles(vehicles);

        vehicles = inventoryService.getVehicles();
        assertThat(vehicles).isNotNull();
        assertThat(vehicles).isNotEmpty();
        assertThat(vehicles.size()).isEqualTo(5);

        Vehicle vehicle = inventoryService.getVehicle(vin1);
        assertThat(vehicle).isNotNull();
        assertThat(vehicle.getVin()).isEqualTo(vin1);

        vehicle = inventoryService.getVehicle(vin2);
        assertThat(vehicle).isNotNull();
        assertThat(vehicle.getVin()).isEqualTo(vin2);
    }
}
