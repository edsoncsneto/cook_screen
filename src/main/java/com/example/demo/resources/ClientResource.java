package com.example.demo.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Client;
import com.example.demo.entities.dtos.ClientDto;
import com.example.demo.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		//resource/controller acessa o repository apenas por meio do service
		List<Client> list = clientService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id){
		Optional<Client> clientOpt = clientService.findById(id);
		if (clientOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Client> save(@RequestBody @Valid ClientDto clientDto){
		Client client = new Client();
		BeanUtils.copyProperties(clientDto, client);
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(client));
	}

}
