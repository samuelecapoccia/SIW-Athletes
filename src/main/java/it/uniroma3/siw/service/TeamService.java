package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.repository.TeamRepository;
import jakarta.transaction.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private PresidentService presidentService;

    // Percorso per salvare le immagini delle squadre
    private static final String UPLOADED_FOLDER = Team.UPLOADED_FOLDER_TEAM;

    /**
     * Metodo per salvare un Team.
     * @param team
     * @return
     */
    public Team save(Team team) {
        return teamRepository.save(team);
    }
    
    public Team getTeam(Long id) {
		Optional<Team> t = teamRepository.findById(id);
		return t.orElse(null);
	}

    /**
     * Metodo per recuperare tutti i Team.
     * @return
     */
    public Iterable<Team> findAll() {
        return teamRepository.findAll();
    }

    /**
     * Metodo per trovare un Team per id.
     * @param id
     * @return
     */
    public Team findById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    /**
     * Metodo per cancellare un Team.
     * @param team
     */
    public void delete(Team team) {
        teamRepository.delete(team);
    }
    
    public String saveTeamImage(MultipartFile file, Team team) {
        String fileName = team.getName() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOADED_FOLDER + fileName);

        try {
            Files.write(path, file.getBytes());
            return "/images/teams/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo per aggiornare i dati di un Team esistente.
     * @param team
     * @return
     */
    public Team update(Team team) {
        Optional<Team> existingTeam = teamRepository.findById(team.getId());

        if (existingTeam.isPresent()) {
            Team updatedTeam = existingTeam.get();
            updatedTeam.setName(team.getName());
            updatedTeam.setFoundationYear(team.getFoundationYear());
            updatedTeam.setAddress(team.getAddress());
            updatedTeam.setPlayers(team.getPlayers());
            updatedTeam.setPresident(team.getPresident());
            updatedTeam.setMainImagePath(team.getMainImagePath());
            return teamRepository.save(updatedTeam);
        } else {
            return null; // Gestisci il caso in cui il team non esiste
        }
    }
    
private void storeFile(MultipartFile p,Team team) {
		
		
		if(!p.isEmpty()) {
        try {
            byte[] bytes = p.getBytes();
            Path path = Paths.get(team.UPLOADED_FOLDER_TEAM + p.getOriginalFilename());
            System.out.println(path);
            Files.write(path, bytes);
            team.setMainImagePath("/images/teams/"+ p.getOriginalFilename());
         } catch (IOException e) {
            e.printStackTrace();
       
         }
    }
}
	
@Transactional
public void saveTeamForAdmin(Team team, MultipartFile preview) {
    // Verifica se l'immagine è stata caricata e salvala
    if (!preview.isEmpty()) {
        try {
            byte[] bytes = preview.getBytes();
            Path path = Paths.get("src/main/resources/static/images/teams/" + preview.getOriginalFilename());
            Files.write(path, bytes);
            
            // Imposta il percorso dell'immagine nel team
            team.setMainImagePath("/images/teams/" + preview.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recupera il Presidente dal database prima di associarlo al team
    President attachedPresident = presidentService.findById(team.getPresident().getId());

    if (attachedPresident != null) {
        team.setPresident(attachedPresident);  // Associa il presidente recuperato dal database
    }

    // Salva il team con il presidente associato
    teamRepository.save(team);
}
	
	public Team CreateTeamAndAddEmptyPlayers() {
		Team team = new Team();
	    for (int i = 0; i < 20; i++) {
	        team.getPlayers().add(new Player());
	    }
	    
	    return team;
	}
	
	@Transactional
    public void deleteTeamById(Long id) {
        // Controllo se il team esiste prima di eliminarlo
        Team team = teamRepository.findById(id).orElse(null);
        if (team != null) {
            teamRepository.deleteById(id);  // Elimina il team
        } else {
            // Puoi gestire il caso in cui il team non esista, ad esempio lanciando un'eccezione o loggando un messaggio
            throw new IllegalArgumentException("Il team con ID " + id + " non esiste.");
        }
    }
	
	@Transactional
	public void updateTeam(Team oldTeam, Team newTeam, MultipartFile file ) {

	    // Aggiorna il nome della squadra se fornito
	    if (newTeam.getName() != null && !newTeam.getName().isBlank()) {
	        oldTeam.setName(newTeam.getName());
	    }

	    // Aggiorna l'anno di fondazione se fornito
	    if (newTeam.getFoundationYear() != 0) {
	        oldTeam.setFoundationYear(newTeam.getFoundationYear());
	    }

	    // Aggiorna l'indirizzo se fornito
	    if (newTeam.getAddress() != null && !newTeam.getAddress().isBlank()) {
	        oldTeam.setAddress(newTeam.getAddress());
	    }


	    // Aggiorna i dettagli del presidente se forniti
	    President oldPresident = oldTeam.getPresident();
	    President newPresident = newTeam.getPresident();

	    if (newPresident != null) {
	        if (newPresident.getName() != null && !newPresident.getName().isBlank()) {
	            oldPresident.setName(newPresident.getName());
	        }
	        if (newPresident.getSurname() != null && !newPresident.getSurname().isBlank()) {
	            oldPresident.setSurname(newPresident.getSurname());
	        }
	        if (newPresident.getCodiceFiscale() != null && !newPresident.getCodiceFiscale().isBlank()) {
	            oldPresident.setCodiceFiscale(newPresident.getCodiceFiscale());
	        }
	        if (newPresident.getBirthDate() != null) {
	            oldPresident.setBirthDate(newPresident.getBirthDate());
	        }
	        if (newPresident.getBirthPlace() != null && !newPresident.getBirthPlace().isBlank()) {
	            oldPresident.setBirthPlace(newPresident.getBirthPlace());
	        }

	        // Aggiorna il presidente nel team
	        oldTeam.setPresident(oldPresident);
	    }

	 // Aggiorna l'immagine principale solo se è stato caricato un nuovo file
	    if (file != null && !file.isEmpty()) {
	    	String filePath = saveTeamImage(file, oldTeam); // Salva l'immagine e ottieni il percorso
	        oldTeam.setMainImagePath(filePath); // Imposta il percorso dell'immagine
	    }

	    // Salva il team aggiornato nel database
	    teamRepository.save(oldTeam);
	}

}