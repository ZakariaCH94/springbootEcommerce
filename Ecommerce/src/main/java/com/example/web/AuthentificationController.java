package com.example.web;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Client;
import com.example.metier.EcommerceMetierImpl;

@Controller
public class AuthentificationController {

	@Autowired
	EcommerceMetierImpl ecommerceMetierImpl;
	
	@RequestMapping("/acces")
	public String index () {
		
		return "login";
	
	} 
	
	
	@RequestMapping(value="/authentifier",method=RequestMethod.GET)
	
	public String Authentifier ( Model model,
			@RequestParam(name="adresseMail",defaultValue="")String adresseMail,
			@RequestParam(name="password",defaultValue="")String password) {
		
		int id;
	try {
			
		Client client=ecommerceMetierImpl.find_client(adresseMail, password);
		id=client.getId();
		
		} catch (Exception e) {
			
			model.addAttribute("exception", e);
			return "login";
		}
		
	
		return "redirect:/home?id="+id;
		
	
	} 
	
	@RequestMapping(value="/creerClient",method=RequestMethod.GET)
	public String creerClient (Model model) {
		
		model.addAttribute("client", new Client());

		return "formClient";
	
	} 
	
	@RequestMapping(value="/saveClient",method=RequestMethod.POST)
	public String saveClient(Model model,
							 @Valid Client client,
							 @RequestParam(name="confpassword",defaultValue="")String confpassword,
							 BindingResult bindingResult
							   ) {
		
		if (bindingResult.hasErrors()) {
			
		
			return "formClient";
		}
		
		String message="";
		
		Client clt=ecommerceMetierImpl.existe_mail(client.getMail());
		
		if (clt!=null) {

		message="adresse mail exite déja";
		
		model.addAttribute("message", message);
		
		return "formClient";
		
		}
		
		else if(!client.getPassword().equals(confpassword)) {
			
			message="la confiramation de mot de passe est incorrecte";
			
			model.addAttribute("message", message);
			
			return "formClient";
		}
		
		else {
		ecommerceMetierImpl.save_client(client);
		message="Client creer avec succee";
		
		return "redirect:acces?message="+message; }
	}
	
	
	
}
