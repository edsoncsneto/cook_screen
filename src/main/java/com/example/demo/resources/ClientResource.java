package com.example.demo.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entities.Client;
import com.example.demo.services.ClientService;

@RestController
@RequestMapping(value = "/users")
public class ClientResource {
	
	@Autowired
	private ClientService userService;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		//resource/controller acessa o repository apenas por meio do service
		List<Client> list = userService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	//a anotação pathVariable serve para indicar que o metodo pegue o argumento que vai vir na url
	public ResponseEntity<Client> findByEntity(@PathVariable Long id){
		Client obj = userService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client obj){
		obj = userService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
//	@PostMapping
//	public ResponseEntity<User> insert(@RequestBody User obj){
//		obj = userService.insert(obj);
//		return ResponseEntity.ok().body(obj);
//	}
	//fazendo com .created(), o valor de sucesso passa a ser 201, e precisamos passar um uri para response entity

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client obj){
		obj = userService.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	

}
