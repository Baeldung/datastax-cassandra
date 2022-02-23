package org.baeldung.springboot.astradb.controller;

import java.util.List;

import org.baeldung.springboot.astradb.entity.ShoppingList;
import org.baeldung.springboot.astradb.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shopping")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping("/list")
    public List<ShoppingList> findAll() {
        return shoppingListService.findAll();
    }

}
