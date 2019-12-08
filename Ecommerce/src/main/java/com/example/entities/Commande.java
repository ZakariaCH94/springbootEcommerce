package com.example.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Commande implements Serializable {

	
	@Id
	@GeneratedValue
	private int id;
	private String date;
	private double quantité;
	
	@ManyToOne
	@JoinColumn(name="CODE_PROD")
	private Produit produit; 
	
	
	@ManyToOne
	@JoinColumn(name="CODE_CLI")
	private Client client;


	public Commande() {
		super();
	}


	public Commande(String date, double quantité, Produit produit, Client client) {
		super();
		this.date = date;
		this.quantité = quantité;
		this.produit = produit;
		this.client = client;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public double getQuantité() {
		return quantité;
	}


	public void setQuantité(double quantité) {
		this.quantité = quantité;
	}


	public Produit getProduit() {
		return produit;
	}


	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
	
}
