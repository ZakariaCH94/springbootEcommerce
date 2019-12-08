package com.example.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Categorie implements Serializable {

	@Id
	@GeneratedValue
	private int idcat;
	private String nom;
	private String photo;
	
	@OneToMany (mappedBy="categorie",fetch=FetchType.LAZY)
	private Collection<Produit> produits;
	
	
	public Categorie() {
		super();
	}


	public Categorie(String nom, String photo, Collection<Produit> produits) {
		super();
		this.nom = nom;
		this.photo = photo;
		this.produits = produits;
	}


	public int getIdcat() {
		return idcat;
	}


	public void setIdcat(int idcat) {
		this.idcat = idcat;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public Collection<Produit> getProduits() {
		return produits;
	}


	public void setProduits(Collection<Produit> produits) {
		this.produits = produits;
	}


	

	
	
}
