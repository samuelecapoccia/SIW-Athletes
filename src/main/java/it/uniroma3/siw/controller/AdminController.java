package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.service.PresidentService;
import it.uniroma3.siw.service.TeamService;
import jakarta.validation.Valid;

@Controller
public class AdminController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
    private PresidentService presidentService;

	@GetMapping("/admin/addTeam")
	public String showTeamForm(Model model) {
		model.addAttribute("team", teamService.CreateTeamAndAddEmptyPlayers());
		model.addAttribute("presidents", presidentService.findAllPresidents());
		return "admin/formNewTeam";
	}
	
	@PostMapping("admin/addTeam")
	public String submitTeamForm(@Valid @ModelAttribute("team") Team team, BindingResult teamBR,
            @RequestParam("file") MultipartFile preview, Authentication auth,
            RedirectAttributes redirectAttributes) {
				if (!teamBR.hasErrors()) {
					teamService.saveTeamForAdmin(team, preview);
				} else {
					redirectAttributes.addFlashAttribute("teamError", true);
				}
				return "redirect:/teams";
	}
	
	@GetMapping("/admin/editTeam")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String showEditTeamForm(@RequestParam Long id, Model model) {
		Team team = teamService.findById(id);
		if(team == null) {
			return "redirect:/teams";
		}
		model.addAttribute("team", team);
		return "admin/formTeam";
	}
	
	@PostMapping("/admin/deleteTeam")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteTeam(@RequestParam Long id, RedirectAttributes redirectAttributes) {
	    teamService.deleteTeamById(id);
	    redirectAttributes.addFlashAttribute("message", "Squadra eliminata con successo!");
	    return "redirect:/teams";
	}
	
	@PostMapping("/admin/editTeam/{id}")
	public String submitTeamForm(@PathVariable("id") Long id,
	                             @Valid @ModelAttribute("team") Team newTeam, 
	                             BindingResult teamBR,
	                             @RequestParam(value = "file", required = false) MultipartFile preview,  // Il file non è obbligatorio
	                             Authentication auth,
	                             RedirectAttributes redirectAttributes) {
	    
	    if (!teamBR.hasErrors()) {
	        Team existingTeam = teamService.getTeam(id);
	        
	        // Controlla se il file è stato caricato
	        if (preview != null && !preview.isEmpty()) {
	            // Gestisci l'aggiornamento dell'immagine se è presente
	            teamService.updateTeam(existingTeam, newTeam, preview);
	        } else {
	            // Aggiorna il team senza cambiare l'immagine
	            teamService.updateTeam(existingTeam, newTeam, null);
	        }
	    } else {
	        redirectAttributes.addFlashAttribute("error", true);
	    }
	    
	    return "redirect:/teams/" + id;
	}
	
}
