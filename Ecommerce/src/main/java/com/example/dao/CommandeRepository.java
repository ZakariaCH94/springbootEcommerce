package com.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Commande;

public interface CommandeRepository extends JpaRepository<Commande,Integer> {

	@Query("select DISTINCT c.date from Commande c where c.client.id =:x")
	public List<Date> listcommandebydate(@Param("x")int idclient);
	
	@Query("select c from Commande c where c.client.id =:x and c.date =:y")
	public List<Commande> listcommandebydate(@Param("x")int idclient, @Param("y")String date);
	
	@Query("select DISTINCT c.date from Commande c where c.client.id =:x")
	public Page<Date> listcommandebydatepage(@Param("x")int idclient,Pageable pageable);
	
	@Query("select c from Commande c where c.client.id =:x and c.date =:y")
	public Page<Commande> listcommandebydatepage(@Param("x")int idclient, @Param("y")String date,Pageable pageable);
}
