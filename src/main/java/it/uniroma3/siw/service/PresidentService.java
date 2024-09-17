package it.uniroma3.siw.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.President;
import it.uniroma3.siw.repository.PresidentRepository;

@Service
public class PresidentService {
	
	@Autowired
	private PresidentRepository presidentRepository;
	
	private static final String PROFILE_PIC_DIR = "src/main/resources/static/images/profilePics";
	
	
	public Iterable<President> findAllPresidents(){
		return presidentRepository.findAll();
	}
	
	public President findById(Long id) {
		return presidentRepository.findById(id).orElse(null);
	}
	
	public void updatePresident(President president, MultipartFile profilePic) throws IOException {
	    // Salva l'immagine di profilo se presente
	    if (profilePic != null && !profilePic.isEmpty()) {
	        // Assicurati che la directory esista
	        File dir = new File(PROFILE_PIC_DIR);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        // Costruisci il percorso per salvare l'immagine
	        String fileName = profilePic.getOriginalFilename();
	        Path filePath = Paths.get(PROFILE_PIC_DIR, fileName);

	        // Salva il file sul disco
	        Files.write(filePath, profilePic.getBytes());
	        
	        if (Files.exists(filePath)) {
	            System.out.println("File salvato con successo: " + filePath.toString());
	        } else {
	            System.out.println("Errore nel salvataggio del file: " + filePath.toString());
	        }

	        // Imposta il percorso dell'immagine nel presidente
	        president.setProfilePic("/images/profilePics/" + fileName);
	    }

	    // Salva o aggiorna il presidente nel database
	    presidentRepository.save(president);
	}

}
