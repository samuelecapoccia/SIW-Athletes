package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.RegistrationForm;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
@Validated
public class AuthController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "login.html";
	}
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("registrationForm", new RegistrationForm());
		return "formRegister";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
	                       BindingResult result, @RequestParam("file") MultipartFile profilePic,
	                       RedirectAttributes redirectAttributes) throws IOException {

	    // Validazione personalizzata per email
	    Credentials credentials = registrationForm.getCredentials();
	    credentialsValidator.validate(credentials, result);

	    if (result.hasErrors()) {
	        return "formRegister"; // Nome del template
	    }

	    // Salva le credenziali e il presidente
	    credentialsService.savePresidentCredentials(credentials, registrationForm.getPresident(), profilePic);
	    return "redirect:/login";
	}
	
	@GetMapping("/myProfile")
	public String myProfile(Authentication auth, Model model) {
	    // Recupera le credenziali dell'utente autenticato
	    Credentials credentials = credentialsService.getCredentials(auth.getName());
	    
	    // Verifica che le credenziali siano valide
	    if (credentials == null) {
	        return "redirect:/login";
	    }
	    
	    President president = credentials.getPresident();

	    // Aggiungi il presidente al modello, anche se manca il nome o il cognome
	    if (president != null) {
	        model.addAttribute("president", president);
	        return "presidentPage";
	    }
	    
	    // Se l'utente non è un presidente, mostra un'altra pagina o messaggio
	    model.addAttribute("message", "Il profilo non è completo o il presidente non è registrato.");
	    return "adminPage"; // Puoi mostrare una pagina alternativa qui
	}
	}
