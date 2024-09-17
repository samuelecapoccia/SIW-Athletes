package it.uniroma3.siw.controller;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.service.PlayerService;
import it.uniroma3.siw.service.PresidentService;
import it.uniroma3.siw.service.TeamService;

@Controller
public class PresidentController {
	
	@Autowired
	private PresidentService presidentService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private PlayerService playerService;
	
	@PostMapping("/updateProfile")
	public String updateProfile(
	        @RequestParam("id") Long id,
	        @RequestParam("name") String name,
	        @RequestParam("surname") String surname,
	        @RequestParam("bio") String bio,
	        @RequestParam("codiceFiscale") String codiceFiscale,
	        @RequestParam("birthDate") String birthDate,
	        @RequestParam("birthPlace") String birthPlace,
	        @RequestParam("profilePic") MultipartFile profilePic,
	        Model model) {

	    System.out.println("ID: " + id);
	    System.out.println("Name: " + name);
	    System.out.println("Surname: " + surname);
	    System.out.println("Bio: " + bio);
	    System.out.println("Codice Fiscale: " + codiceFiscale);
	    System.out.println("Birth Date: " + birthDate);
	    System.out.println("Birth Place: " + birthPlace);
	    System.out.println("ProfilePic: " + (profilePic != null ? profilePic.getOriginalFilename() : "No file uploaded"));

	    try {
	        President existingPresident = presidentService.findById(id);
	        if (existingPresident != null) {
	            existingPresident.setName(name);
	            existingPresident.setSurname(surname);
	            existingPresident.setBio(bio);
	            existingPresident.setCodiceFiscale(codiceFiscale);
	            existingPresident.setBirthDate(birthDate);
	            existingPresident.setBirthPlace(birthPlace);

	            if (profilePic != null && !profilePic.isEmpty()) {
	                presidentService.updatePresident(existingPresident, profilePic);
	            } else {
	                presidentService.updatePresident(existingPresident, null);
	            }

	            model.addAttribute("message", "Profilo aggiornato con successo!");
	        } else {
	            model.addAttribute("errorMessage", "Presidente non trovato.");
	        }
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "Errore durante l'aggiornamento del profilo: " + e.getMessage());
	    }

	    return "redirect:/myProfile";
	}

	@PostMapping("/teams/{id}/addPlayer")
	public String addPlayerToTeam(@PathVariable Long id, @ModelAttribute Player player, Principal principal) throws AccessDeniedException {
	    Team team = teamService.findById(id);
	    President president = team.getPresident();

	    // Verifica che il presidente loggato sia quello della squadra
	    if (president == null || !president.getCredentials().getEmail().equals(principal.getName())) {
	        throw new AccessDeniedException("Non hai il permesso di modificare questa squadra.");
	    }
	    
	    Player managedPlayer = playerService.merge(player);
	    
	    team.addPlayer(managedPlayer);
	    teamService.save(team); 

	    return "redirect:/teams/" + id; 
	}

	@PostMapping("/teams/{id}/removePlayer")
	public String removePlayerFromTeam(@PathVariable Long id, @RequestParam Long playerId, Principal principal) throws AccessDeniedException {
	    Team team = teamService.findById(id);
	    President president = team.getPresident();

	    // Verifica che il presidente loggato sia quello della squadra
	    if (president == null || !president.getCredentials().getEmail().equals(principal.getName())) {
	        throw new AccessDeniedException("Non hai il permesso di modificare questa squadra.");
	    }

	    Player player = playerService.findById(playerId);
	    team.removePlayer(player);
	    teamService.save(team); // Assicurati di salvare le modifiche nel database

	    return "redirect:/teams/" + id; // Reindirizza alla pagina di dettaglio del team
	}
}
