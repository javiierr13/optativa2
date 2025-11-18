package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Pizza;
import com.daw.persistence.repositories.PizzaRepository;
import com.daw.services.exceptions.PizzaNotFoundException;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	public List<Pizza> findAll() {
		return this.pizzaRepository.findAll();
	}

	public Pizza findById(int idPizza) {
		if (!this.pizzaRepository.existsById(idPizza)) {
			throw new PizzaNotFoundException("El ID indicado no existe. ");
		}

		return this.pizzaRepository.findById(idPizza).get();
	}

	public Pizza create(Pizza pizza) {
		pizza.setId(0);

		return this.pizzaRepository.save(pizza);
	}

	public Pizza update(int idPizza, Pizza pizza) {
		if (idPizza != pizza.getId()) {
			throw new PizzaNotFoundException("El ID del path y del body no coinciden. ");
		}

		Pizza pizzaBD = this.findById(idPizza);
		pizzaBD.setDescripcion(pizza.getDescripcion());
		pizzaBD.setDisponible(pizza.isDisponible());
		pizzaBD.setNombre(pizza.getNombre());
		pizzaBD.setPrecio(pizza.getPrecio());
		pizzaBD.setVegana(pizza.isVegana());
		pizzaBD.setVegetariana(pizza.isVegetariana());

		return this.pizzaRepository.save(pizzaBD);
	}

	public void deleteById(int idPizza) {
		if (!this.pizzaRepository.existsById(idPizza)) {
			throw new PizzaNotFoundException("El ID indicado no existe. ");
		}

		this.pizzaRepository.deleteById(idPizza);
	}

	public List<Pizza> findDisponiblesOrdenPrecio() {
		return this.pizzaRepository.findByDisponibleTrueOrderByPrecioAsc();
	}

	public List<Pizza> findByNombreDisponible(String nombre) {
		return this.pizzaRepository.findByNombreContainingIgnoreCaseAndDisponibleTrue(nombre);
	}

	public List<Pizza> findConIngrediente(String ingrediente) {
		return this.pizzaRepository.findByDescripcionContainingIgnoreCase(ingrediente);
	}

	public List<Pizza> findSinIngrediente(String ingrediente) {
		return this.pizzaRepository.findByDescripcionNotContainingIgnoreCase(ingrediente);
	}

	public Pizza actualizarPrecio(int idPizza, double precio) {
		Pizza pizzaBD = this.findById(idPizza);
		pizzaBD.setPrecio(precio);
		return this.pizzaRepository.save(pizzaBD);
	}
	
	public Pizza cambiarDisponibilidad(int idPizza, boolean disponible) {
	    Pizza pizzaBD = this.findById(idPizza);
	    pizzaBD.setDisponible(disponible);
	    return this.pizzaRepository.save(pizzaBD);
	}


}