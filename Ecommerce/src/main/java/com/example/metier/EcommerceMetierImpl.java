package com.example.metier;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CategorieRepository;
import com.example.dao.ClientRepository;
import com.example.dao.CommandeRepository;
import com.example.dao.ProduitRepository;
import com.example.entities.Categorie;
import com.example.entities.Client;
import com.example.entities.Commande;
import com.example.entities.Produit;

@Service
@Transactional
public class EcommerceMetierImpl implements IEcommerceMetier {

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	CategorieRepository categorieRepository;
	
	@Autowired
	ProduitRepository produitRepository;
	
	@Autowired
	CommandeRepository commandeRepository;
	
	@Override
	public void save_client(Client client) {

		clientRepository.save(client);
	}

	@Override
	public Client find_client(String mail, String password) {

		String message="";
		Client client=clientRepository.findByMailAndPassword(mail,password);
		
		if (client==null) throw new RuntimeException("client introuvable: compte inéxistant / passeword or mail incorrecte");
		return client;
	}

	@Override
	public Client existe_mail(String mail) {

	
		
		Client client=clientRepository.findByMail(mail);
		
		return client;
		
	}

	@Override
	public void save_catégorie(Categorie categorie) {
		
		categorieRepository.save(categorie);
		
	}

	@Override
	public Client find_clientid(int id) {

		Optional<Client> client1=clientRepository.findById(id);
		Client client=client1.get();
		return client;
	}

	@Override
	public List<Categorie> find_all_catégorie() {

		List<Categorie> liste=categorieRepository.findAll();
				
		return liste;
	}

	@Override
	public Categorie find_categorie_id(int id) {

		Optional<Categorie> categorie1 = categorieRepository.findById(id);
		Categorie categorie =categorie1.get();
		return categorie;
	}

	@Override
	public void save_produit(Produit produit) {
		
		produitRepository.save(produit);
		
	}

	@Override
	public List<Produit> find_produitByidCat(int idcat,int etat) {

		
		return produitRepository.listproduit(idcat,etat);
		
	}

	@Override
	public Produit find_produit_id(int id) {

		Optional<Produit> produit1=produitRepository.findById(id);
		Produit produit=produit1.get();	
		return produit;
	}

	@Override
	public void save_commande(Commande commande) {

		
		
		commandeRepository.save(commande);
	}

	@Override
	public void supprimer_produit(int id) {

		produitRepository.deleteById(id);
	}

	@Override
	public List<Date> find_commandebyclientbydate(int idclient) {

		List<Date> listecommandedate=commandeRepository.listcommandebydate(idclient);
		return listecommandedate;
	}

	@Override
	public List<Commande> listcommandebydate(int idclient, String date) {

		List<Commande> listecommande=commandeRepository.listcommandebydate(idclient, date);

		
		return listecommande;
	}

	@Override
	public Page<Date> listcommandebydatepage(int idclient,int page, int size) {
		
		Page<Date> listecommande=commandeRepository.listcommandebydatepage(idclient,new PageRequest(page, size));
		
		return listecommande;
	}

	@Override
	public Page<Commande> listcommandebydatepage(int idclient, String date, int page, int size) {
		
		Page<Commande> listecommande=commandeRepository.listcommandebydatepage(idclient, date, new PageRequest(page, size));

		return listecommande;
	}

	@Override
	public void supprimer_categorie(int id) {

		
		categorieRepository.deleteById(id);
	}

	@Override
	public Produit find_produit_nom(String nom) {
		
		Produit produit=produitRepository.findByNom(nom);
		return produit;
	}

	@Override
	public void update_produit(int quantitéenstock, int etat,double prix,String nom) {
		
		produitRepository.update_produit(quantitéenstock, etat,prix,nom);
		
	}

	@Override
	public void update_produit_commande(int etat, String nom) {

		produitRepository.update_produit_commande(etat, nom);
	}


}
