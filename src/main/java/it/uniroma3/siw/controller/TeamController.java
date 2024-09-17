package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.repository.TeamRepository;
import it.uniroma3.siw.service.TeamService;

@Controller
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@GetMapping("/teams")
	public String getTeams(Model model) {
		model.addAttribute("teams", teamService.findAll());
		return "teams.html";
	}
	
	@GetMapping("/teams/{id}")
    public String getTeamDetails(@PathVariable("id") Long id, Principal principal, Model model) {
        // Recupera la squadra dal repository tramite l'id
        Team team = teamRepository.findById(id).orElse(null);
        President president = team.getPresident();
     // Costruisci il percorso completo per l'immagine del profilo
        String profilePicPath = (president.getProfilePic() != null && !president.getProfilePic().isEmpty()) 
                                ? "/images/profilePics/" + president.getProfilePic() 
                                : "/images/defaultimage.png";
        // Controlla se la squadra esiste
        if (team != null) {
            model.addAttribute("team", team);
            model.addAttribute("president", president);
            model.addAttribute("profilePicPath", profilePicPath);
            model.addAttribute("players", team.getPlayers()); // Assumi che il team abbia una lista di giocatori
         // Aggiungi un oggetto Player vuoto per il form
            model.addAttribute("newPlayer", new Player());  // <-- Aggiungi questo
        }
        
        if (principal != null) {
            String username = principal.getName(); // Email dell'utente loggato
            boolean isPresident = username.equals(president.getCredentials().getEmail());
            model.addAttribute("isPresident", isPresident);
        } else {
            model.addAttribute("isPresident", false);
        }

        // Restituisce il template "teamDetails.html"
        return "team";
    }
}	

