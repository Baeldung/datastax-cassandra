package org.baeldung.springboot.astradb.controller;

import static org.mockito.Mockito.verify;

import org.baeldung.springboot.astradb.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShoppingListControllerUnitTest {

    @Mock
    private ShoppingListService service;

    @InjectMocks
    private ShoppingListController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void whenFindAll_thenInvokesRepository() {
        controller.findAll();

        verify(service).findAll();
    }

}
