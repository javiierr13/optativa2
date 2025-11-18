package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

	List<Pizza> findByDisponibleTrueOrderByPrecioAsc();

	List<Pizza> findByNombreContainingIgnoreCaseAndDisponibleTrue(String nombre);

	List<Pizza> findByDescripcionContainingIgnoreCase(String descripcion);

	List<Pizza> findByDescripcionNotContainingIgnoreCase(String descripcion);

}