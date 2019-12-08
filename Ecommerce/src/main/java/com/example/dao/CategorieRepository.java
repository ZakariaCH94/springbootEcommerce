package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie,Integer> {

	public Categorie findByNom(String nom);

}
