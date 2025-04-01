package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizza> index() {
        return pizzaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id) {

        // gestisco il not_found
        Optional<Pizza> pizzaToFind = pizzaService.findById(id);
        if (pizzaToFind.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Pizza>(pizzaToFind.get(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {

        return new ResponseEntity<Pizza>(pizzaService.create(pizza), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Pizza> update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {

        // gestisco il not_found
        Optional<Pizza> pizzaToFind = pizzaService.findById(id);
        if (pizzaToFind.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }

        pizzaService.update(pizza);
        return new ResponseEntity<Pizza>(pizzaToFind.get(), HttpStatus.OK);
    }

}
