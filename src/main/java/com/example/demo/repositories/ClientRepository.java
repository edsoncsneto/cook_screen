package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entities.Client;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long>{

	Optional<Client> findByPhone(String phone);
	
}
