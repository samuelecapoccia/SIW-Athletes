package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.repository.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public Player findById(Long id) {
		return playerRepository.findById(id).orElse(null);
	}
	
	@Transactional
    public Player merge(Player player) {
        return entityManager.merge(player);  // Unisce il player staccato con quello gestito
    }
}
