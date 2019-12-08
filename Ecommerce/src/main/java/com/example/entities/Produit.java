package com.example.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Produit implements Serializable {

	@Id
	@GeneratedValue
	private int idprod;
	@NotEmpty
	private String nom;
	private int quantitéenstock;
	private double prix;
	private String photo;
	private int etat;
	
	@ManyToOne
	@JoinColumn(name="CODE_CAT")
	private Categorie categorie;
	
	@OneToMany (mappedBy="produit",fetch=FetchType.LAZY)
	private Collection<Commande> commandes;

	public Produit() {
		super();
	}

	public Produit(@NotEmpty String nom, int quantitéenstock, double prix, String photo, Categorie categorie,
			Collection<Commande> commandes,int etat) {
		super();
		this.nom = nom;
		this.quantitéenstock = quantitéenstock;
		this.prix = prix;
		this.photo = photo;
		this.categorie = categorie;
		this.commandes = commandes;
		this.etat = etat;
	}

	public int getIdprod() {
		return idprod;
	}

	public void setIdprod(int idprod) {
		this.idprod = idprod;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getQuantitéenstock() {
		return quantitéenstock;
	}

	public void setQuantitéenstock(int quantitéenstock) {
		this.quantitéenstock = quantitéenstock;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Collection<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(Collection<Commande> commandes) {
		this.commandes = commandes;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
	
	
	
}
