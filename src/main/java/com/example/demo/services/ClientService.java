package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Client;
import com.example.demo.entities.dtos.ClientDto;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> findAll(){
		return clientRepository.findAll();
	}
	
	public Client findById(Long id) {
		Optional<Client> clientOpt = clientRepository.findById(id);
		return clientOpt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	
	public void delete(Long id) {
		try {
			if (!clientRepository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			clientRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Client update(Long id, Client client) {
		try {
			Client entity = clientRepository.getOne(id);
			updateData(entity, client);
			return clientRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(Client entity, Client client) {
		entity.setName(client.getName());
		entity.setPhone(client.getPhone());
	}
	//TODO: verificar exceção de integridade
	public Client getOrCreateClientByPhone(ClientDto clientDto) {
		Optional<Client> clientOpt = clientRepository.findByPhone(clientDto.phone());
		if (clientOpt.isEmpty()) {
			Client client = new Client();
			client.setName(clientDto.name());
			client.setPhone(clientDto.phone());
			return save(client);
		}
		return clientOpt.get();
	}

}
