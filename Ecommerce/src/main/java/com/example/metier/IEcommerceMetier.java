package com.example.metier;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import com.example.entities.Categorie;
import com.example.entities.Client;
import com.example.entities.Commande;
import com.example.entities.Produit;

public interface IEcommerceMetier {

	public void save_client (Client client);
	public Client find_client (String mail,String password);
	public Client existe_mail (String mail);
	public void save_catégorie (Categorie categorie);
	public Client find_clientid (int id);
	public List<Categorie> find_all_catégorie();
	public Categorie find_categorie_id (int id);
	public Produit find_produit_id (int id);
	public Produit find_produit_nom (String nom);
	public void update_produit (int quantitéenstock ,int etat ,double prix,String nom);
	public void update_produit_commande(int etat,String nom);
	public void save_produit (Produit produit);
	public void supprimer_produit (int id);
	public void supprimer_categorie (int id);
	public void save_commande (Commande commande);
	public List<Produit> find_produitByidCat (int idcat,int etat);
	public List<Date> find_commandebyclientbydate (int idclient);
	public List<Commande> listcommandebydate(int idclient,String date);
	public Page<Date> listcommandebydatepage(int idclient,int page,int size);
	public Page<Commande> listcommandebydatepage(int idclient,String date,int page,int size);
	




	

}
