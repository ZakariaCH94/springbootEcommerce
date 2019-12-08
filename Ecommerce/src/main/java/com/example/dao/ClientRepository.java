package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Client;

public interface ClientRepository extends JpaRepository<Client,Integer>{

	public Client findByMailAndPassword (String mail,String Password);
	public Client findByMail(String mail);

}
