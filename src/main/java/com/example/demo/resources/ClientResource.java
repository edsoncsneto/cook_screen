package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dtos.ClientDto;
import com.example.demo.model.entities.Client;
import com.example.demo.services.ClientService;

import jakarta.validation.Valid;

/*findAll
 *findById
 *delete
 *update
 */

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> list = clientService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id){
		Client client = clientService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(client);
	}
	
	@PutMapping("/{id}")	
	public ResponseEntity<Object> update(@PathVariable(value="id") Long id, @RequestBody @Valid ClientDto clientDto){
		Client client = new Client();
		BeanUtils.copyProperties(clientDto, client);
		client = clientService.update(id, client);
		return ResponseEntity.status(HttpStatus.OK).body(client);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
