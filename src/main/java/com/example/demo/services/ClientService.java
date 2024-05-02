package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Client;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository userRepository;
	
	public List<Client> findAll(){
		return userRepository.findAll();
	}
	
	public Client findById(Long id) {
		Optional<Client> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
		//o método .get() no objeto optional serve para pegar o resultado armazenado
	}
	
	public Client insert(Client obj) {
		return userRepository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}
	
	public Client update(Long id, Client obj) {
		try {
			Client entity = userRepository.getReferenceById(id);
			updateData(entity, obj);
			return userRepository.save(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		
	}

	private void updateData(Client entity, Client obj) {
		entity.setName(obj.getName());
		entity.setPhone(obj.getPhone());	
	}

}
