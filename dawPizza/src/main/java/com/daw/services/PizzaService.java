package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pizza;
import com.daw.persistence.repositories.PizzaRepository;
import com.daw.services.exceptions.PizzaException;
import com.daw.services.exceptions.PizzaNotFoundException;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	// findAll
	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	// findById
	public Pizza findById(int id) {
		if (!pizzaRepository.existsById(id)) {
			throw new PizzaNotFoundException("La pizza con ID " + id + " no existe");
		}
		return pizzaRepository.findById(id).get();
	}

	// create
	public Pizza create(Pizza pizza) {
		if (pizza.getNombre() == null || pizza.getNombre().isBlank()) {
			throw new PizzaException("El nombre de la pizza es obligatorio");
		}
		if (pizza.getPrecio() <= 0) {
			throw new PizzaException("El precio debe ser mayor que 0");
		}
		pizza.setDisponible(true);
		return pizzaRepository.save(pizza);
	}

	// update
	public Pizza update(Pizza pizza, int id) {
		Pizza pizzaExistente = findById(id);

		pizzaExistente.setNombre(pizza.getNombre());
		pizzaExistente.setDescripcion(pizza.getDescripcion());
		pizzaExistente.setPrecio(pizza.getPrecio());
		pizzaExistente.setVegetariana(pizza.isVegetariana());
		pizzaExistente.setVegana(pizza.isVegana());
		pizzaExistente.setDisponible(pizza.isDisponible());

		return pizzaRepository.save(pizzaExistente);
	}

	// delete
	public void delete(int id) {
		if (!pizzaRepository.existsById(id)) {
			throw new PizzaNotFoundException("La pizza con ID " + id + " no existe");
		}
		pizzaRepository.deleteById(id);
	}
}
