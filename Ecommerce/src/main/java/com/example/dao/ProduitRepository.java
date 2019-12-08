package com.example.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Categorie;
import com.example.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit,Integer> {

	
	@Query("select p from Produit p where p.categorie.idcat =:x and p.etat =:y")
	public List<Produit> listproduit(@Param("x")int idcat,@Param("y")int etat);
	
	public Produit findByNom(String nom);
	
	@Modifying
	@Query("UPDATE Produit p SET p.quantitéenstock =:a, p.etat =:b, p.prix =:c where p.nom=:x")
	public void update_produit(@Param("a")int quantitéenstock,@Param("b") int etat,@Param("c") double prix, @Param("x")String nom);
	
	@Modifying
	@Query("UPDATE Produit p SET p.etat =:b where p.nom=:x")
	public void update_produit_commande(@Param("b") int etat, @Param("x")String nom);
}
