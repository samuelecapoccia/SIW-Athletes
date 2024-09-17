package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.repository.PlayerRepository;
import jakarta.validation.Valid;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@GetMapping("/add/{teamId}")
	public String addPlayerForm(@PathVariable Long teamId, Model model) {
		model.addAttribute("player", new Player());
		model.addAttribute("teamId", teamId);
		return "addPlayers.html";
	}
	
	@PostMapping("/players/save")
	public String savePlayer(@ModelAttribute("player") @Valid Player player, BindingResult result) {
		if(result.hasErrors()) {
			return ("addPlayers.html");
		}
		playerRepository.save(player);
		return "redirect:/teams/details/" + player.getTeam().getId();
	}
}
