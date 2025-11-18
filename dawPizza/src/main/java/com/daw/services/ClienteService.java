package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.Cliente;
import com.daw.persistence.repositories.ClienteRepository;
import com.daw.services.exceptions.ClienteNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}

	public Cliente findById(int idCliente) {
		if (!this.clienteRepository.existsById(idCliente)) {
			throw new ClienteNotFoundException("El ID indicado no existe.");
		}

		return this.clienteRepository.findById(idCliente).get();
	}

	public Cliente create(Cliente cliente) {
		cliente.setId(0);
		return this.clienteRepository.save(cliente);
	}

	public Cliente update(int idCliente, Cliente cliente) {
		Cliente clienteBD = this.findById(idCliente);
		clienteBD.setNombre(cliente.getNombre());
		clienteBD.setDireccion(cliente.getDireccion());
		clienteBD.setEmail(cliente.getEmail());
		clienteBD.setTelefono(cliente.getTelefono());

		return this.clienteRepository.save(clienteBD);
	}

	public void deleteById(int idCliente) {
		if (!this.clienteRepository.existsById(idCliente)) {
			throw new ClienteNotFoundException("El ID indicado no existe.");
		}

		this.clienteRepository.deleteById(idCliente);
	}

	public Cliente actualizarDireccion(int idCliente, String nuevaDireccion) {
		Cliente clienteBD = this.findById(idCliente);
		clienteBD.setDireccion(nuevaDireccion);
		return this.clienteRepository.save(clienteBD);
	}

	public Cliente findByTelefono(String tlf) {
		Cliente clienteBD = this.clienteRepository.findByTelefono(tlf);
		if (clienteBD == null) {
			throw new ClienteNotFoundException("No existe cliente con ese teléfono.");
		}
		return clienteBD;
	}

}
