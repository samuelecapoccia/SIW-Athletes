package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService  {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;
    
   
    @Transactional
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credentials getCredentials(String email) {
        Optional<Credentials> result = this.credentialsRepository.findByEmail(email);
        return result.orElse(null);
    }
    
    @Transactional
    public Credentials getCredentials(President president) {
        Optional<Credentials> result = this.credentialsRepository.findByPresident(president);
        return result.orElse(null);
    }


    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public String updatePassword(String psw) {
    	return this.passwordEncoder.encode(psw);
    }
    
    
    @Transactional
    public void deleteCredentials(Long credentialsId) {
        Credentials credentials = credentialsRepository.findById(credentialsId).orElse(null);
        if(credentials!=null) credentialsRepository.delete(credentials);
    }

    
    @Transactional
    public void savePresidentCredentials(Credentials c,President p,MultipartFile file) {
    	 
    	 if (!file.isEmpty()) {
             try {
                 byte[] bytes = file.getBytes();
                 Path path = Paths.get(President.UPLOADED_FOLDER_PROFILEPICS + file.getOriginalFilename());
                 System.out.println(path);
                 Files.write(path, bytes);
                 p.setProfilePic("/images/profilePics/"+ file.getOriginalFilename());
              } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    	 
    	 c.setRole(Credentials.PRESIDENT_ROLE);

         c.setPresident(p);
         this.saveCredentials(c);	
    }
    

	public boolean emailExists(String email) {
            return credentialsRepository.existsByEmail(email);
	}

	public boolean isAdminLogged(Authentication auth) {
		Credentials c = this.getCredentials(auth.getName());
		return c.getRole().equals("ADMIN");

	}

}  
    
 
