package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Pizza;
import com.daw.services.PizzaService;
import com.daw.services.exceptions.PizzaException;
import com.daw.services.exceptions.PizzaNotFoundException;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	@GetMapping
	public ResponseEntity<List<Pizza>> findAll() {
		return ResponseEntity.ok(pizzaService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(pizzaService.findById(id));
		} catch (PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pizza pizza) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.create(pizza));
		} catch (PizzaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody Pizza pizza) {
		try {
			return ResponseEntity.ok(pizzaService.update(pizza, id));
		} catch (PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (PizzaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			pizzaService.delete(id);
			return ResponseEntity.ok().build();
		} catch (PizzaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
