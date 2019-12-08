package com.example.web;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Categorie;
import com.example.entities.Client;
import com.example.entities.Commande;
import com.example.entities.Produit;
import com.example.metier.EcommerceMetierImpl;


@Controller
public class EcommerceController {

	@Autowired
	EcommerceMetierImpl ecommerceMetierImpl;
	
	@Value("${dir.imagescat}")
	private String imageDircat;
	
	@Value("${dir.imagesprod}")
	private String imageDirprod;
	
	
	
	@RequestMapping(value="/home")
	
	public String home(Model model,@RequestParam(name="id")int id) {
				
		Client client=ecommerceMetierImpl.find_clientid(id);
		model.addAttribute("client", client);
		
		List<Categorie> liste= ecommerceMetierImpl.find_all_catégorie();
		
		model.addAttribute("listecategorie", liste);
	
		return "home";
	
	}

	
	@RequestMapping(value="/ajouterCategorie",method=RequestMethod.GET)
	public String  ajouterCategorie (Model model) {
	
		model.addAttribute("categorie", new Categorie());
		
		return "FormCategorie";
	} 
	
	@RequestMapping(value="/saveCategorie",method=RequestMethod.POST)
	public String saveEtudiant(Model model,@Valid Categorie cat,
							   BindingResult bindingResult,
							   @RequestParam(name="id") int id,
							   @RequestParam(name="picture") MultipartFile file) throws Exception {
		
		if (bindingResult.hasErrors()) {
			
			return "formCategorie";
		}
		
		
		if (!file.isEmpty()) {
			
			cat.setPhoto(file.getOriginalFilename());
			ecommerceMetierImpl.save_catégorie(cat);
			file.transferTo(new File(imageDircat+cat.getIdcat()));
		}
		
		return "redirect:home?id="+id;
	}
	
	@RequestMapping(value="/getPhoto",produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(int id) throws Exception {
		
		File f=new File(imageDircat+id);
		
		byte[] bytesArray = new byte[(int) f.length()]; 

		  FileInputStream fis = new FileInputStream(f);
		  fis.read(bytesArray); //read file into bytes[]
		  fis.close();
					
		  return bytesArray;
	}
	
	@RequestMapping(value="/ajouterProduit",method=RequestMethod.GET)
	public String  ajouterProduit (Model model) {
	
		List<Categorie> liste= ecommerceMetierImpl.find_all_catégorie();
		model.addAttribute("listeCategorie", liste);
		model.addAttribute("produit", new Produit());
		
		return "formProduit";
	} 
	
	@RequestMapping(value="/saveProduit",method=RequestMethod.POST)
	public String saveProduit(Model model,@Valid Produit prod,
							   BindingResult bindingResult,
							   @RequestParam(name="id") int id,
							   @RequestParam(name="categorie") int cat,
							   @RequestParam(name="pictures") MultipartFile file) throws Exception {
		
		if (bindingResult.hasErrors()) {
			
			List<Categorie> liste= ecommerceMetierImpl.find_all_catégorie();
			model.addAttribute("listeCategorie", liste);
			return "formProduit";
		}
		
	
		if (!file.isEmpty()) {
			
			Produit produit=ecommerceMetierImpl.find_produit_nom(prod.getNom());
			
			if (produit==null) {
			
			prod.setPhoto(file.getOriginalFilename());
			Categorie categorie=ecommerceMetierImpl.find_categorie_id(cat);
			prod.setCategorie(categorie);
			prod.setEtat(1);
			ecommerceMetierImpl.save_produit(prod);
			file.transferTo(new File(imageDirprod+prod.getIdprod())); }
			
			else {
				
				ecommerceMetierImpl.update_produit(prod.getQuantitéenstock(),1,prod.getPrix(),prod.getNom());
			}
		}
		
		return "redirect:home?id="+id;
	}
	
	@RequestMapping(value="/nosproduits")
	public String  afficherProduit (Model model,
									@RequestParam(name="idClient") int idclient,
									@RequestParam(name="idCategorie") int id) {
	
		Client client=ecommerceMetierImpl.find_clientid(idclient);
		model.addAttribute("client", client);
		
		Categorie categorie=ecommerceMetierImpl.find_categorie_id(id);
		model.addAttribute("categorie", categorie);
		
		List<Produit> liste= ecommerceMetierImpl.find_produitByidCat(id,1);
		model.addAttribute("listeproduit", liste);
		
		return "nosproduits";
	} 
	
	
	@RequestMapping(value="/getPhotoprod",produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhotoProd(int id) throws Exception {
		
		File f=new File(imageDirprod+id);
		
		byte[] bytesArray = new byte[(int) f.length()]; 

		  FileInputStream fis = new FileInputStream(f);
		  fis.read(bytesArray); //read file into bytes[]
		  fis.close();
					
		  return bytesArray;
	}
	
	@RequestMapping(value="/commander",method=RequestMethod.GET)
	public String  commander (Model model,
			@RequestParam(name="idClient") int idclient,
			@RequestParam(name="idCategorie") int idcat,
			@RequestParam(name="idProduit") int idprod) {
	
		
		Categorie categorie =ecommerceMetierImpl.find_categorie_id(idcat);
		Produit produit= ecommerceMetierImpl.find_produit_id(idprod);
		
		model.addAttribute("idclient",idclient);
		model.addAttribute("Categorie",categorie);
		model.addAttribute("Produit",produit);
		model.addAttribute("quantitestock",produit.getQuantitéenstock());


		
		return "formCommande";
	} 
	
	@RequestMapping(value="/saveCommande",method=RequestMethod.POST)
	public String saveCommande(Model model,
							   @RequestParam(name="idClient") int idclient,
							   @RequestParam(name="idProduit") int idproduit,
							   @RequestParam(name="idCategorie") int idCategorie,
							   @RequestParam(name="quantite") int quantite
							   
							   ) {
		
	
		
		Produit produit=ecommerceMetierImpl.find_produit_id(idproduit);
		Client client=ecommerceMetierImpl.find_clientid(idclient);
		
		if (produit.getQuantitéenstock()>=quantite) {
		
		int reststock=produit.getQuantitéenstock()-quantite;
		produit.setQuantitéenstock(reststock);
		
	
		SimpleDateFormat formater = null;

	    Date aujourdhui = new Date();

	    formater = new SimpleDateFormat("yyyy-MM-dd");
		
		Commande commande =new Commande();
		
		commande.setProduit(produit);
		commande.setClient(client);
		commande.setQuantité(quantite);
		
		 
		commande.setDate(formater.format(aujourdhui));
		
		ecommerceMetierImpl.save_commande(commande); 
	
		String message="commande effectuee avec succees";
		
		if (produit.getQuantitéenstock()==0) ecommerceMetierImpl.update_produit_commande(0, produit.getNom());

		
		return "redirect:home?id="+idclient+"&message="+message;
		
		}

		else {
		
	String message="vous pouvez pas effectuer la commande, la quantite demander non disponible";
	return "redirect:commander?idClient="+idclient+"&idCategorie="+idCategorie+"&idProduit="+idproduit+"&quantite="+quantite+"&message="+message;

	
		
		}
		
	}
	
	
	@RequestMapping(value="/monPanier")
	public String  panier (Model model,
						   @RequestParam(name="id") int idclient,
						   @RequestParam(name="page",defaultValue="0")int page,
						   @RequestParam(name="size",defaultValue="4")int size) {
	
	
	    //List<Date> listecommandebydate=ecommerceMetierImpl.find_commandebyclientbydate(idclient);
		//model.addAttribute("liste",listecommandebydate);
		
		Page<Date> listecommandepage=ecommerceMetierImpl.listcommandebydatepage(idclient,page,size);
		
		model.addAttribute("listecommandepage",listecommandepage.getContent());
		
		int [] pages=new int[listecommandepage.getTotalPages()];
		for (int i=0;i<listecommandepage.getTotalPages();i++) pages[i]=i;

		model.addAttribute("pages", pages);
		model.addAttribute("pageCourant", page);

		model.addAttribute("idclient", idclient);
		Client client=ecommerceMetierImpl.find_clientid(idclient);
		model.addAttribute("client", client);
		
		return "monpanier";
	} 
	
	@RequestMapping(value="/consulterpanier")
	public String  consulterpanier (Model model,
						   @RequestParam(name="idClient") int idclient,
						   @RequestParam(name="date") String date,
						   @RequestParam(name="page",defaultValue="0")int page,
						   @RequestParam(name="size",defaultValue="4")int size
						   ) {
	
	
	
		Client client=ecommerceMetierImpl.find_clientid(idclient);
		model.addAttribute("client", client);
		
		Page<Commande> listecommande=ecommerceMetierImpl.listcommandebydatepage(idclient, date, page, size);
		
		int [] pages=new int [listecommande.getTotalPages()];
		for (int i=0;i<listecommande.getTotalPages();i++) pages[i]=i;
		model.addAttribute("listecommande", listecommande);
		model.addAttribute("pages",pages);
		model.addAttribute("date", date);
		
		model.addAttribute("pageCourant", page);
		
		//List<Commande> listecommande=ecommerceMetierImpl.listcommandebydate(idclient, date);
		
		//model.addAttribute("listecommande", listecommande);
		
		
	
		
		return "monpanierdetails";
	} 
	
	
	@RequestMapping(value="/supprimercat")
	public String supprimercat(@RequestParam(name="idcat")int idcat,@RequestParam(name="id")int id) {
		
		ecommerceMetierImpl.supprimer_categorie(idcat);
		return "redirect:home?id="+id;
	}
	
	@RequestMapping(value="/supprimerprod")
	public String supprimerprod(@RequestParam(name="idprod")int idprod,@RequestParam(name="id")int id) {
		
		ecommerceMetierImpl.supprimer_produit(idprod);
		return "redirect:home?id="+id;
	}
	
	
	
}
