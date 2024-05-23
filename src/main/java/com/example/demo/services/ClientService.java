package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Client;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> findAll(){
		return clientRepository.findAll();
	}
	
	public Client findById(Long id) throws ResourceNotFoundException{
		Optional<Client> clientOpt = clientRepository.findById(id);
		if (clientOpt.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		return clientOpt.get();
	}
	
	public Client save(Client obj) {
		return clientRepository.save(obj);
	}
	
	public Optional<Client> findByPhone(String phone) {
		return clientRepository.findByPhone(phone);
	}

}
