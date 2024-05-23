package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Client;
import com.example.demo.entities.dtos.ClientDto;
import com.example.demo.services.ClientService;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> list = clientService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	//TODO: PRIORIDADE: deixar a estrutura de todos os resources da seguinte maneira (há mudanças no service)
	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id){
		try {
            Client client = clientService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(client);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
	}
	
	@PostMapping
	public ResponseEntity<Client> save(@RequestBody @Valid ClientDto clientDto){
		Client client = new Client();
		BeanUtils.copyProperties(clientDto, client);
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(client));
	}
	
	@PutMapping("/{id}")	
	public ResponseEntity<Client> update(@PathVariable(value="id") Long id, @RequestBody @Valid ClientDto clientDto){
		try {
			Client client = clientService.findById(id);
			client.setPhone(clientDto.phone());
			client.setName(clientDto.name());
			return ResponseEntity.status(HttpStatus.OK).body(clientService.save(client));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
