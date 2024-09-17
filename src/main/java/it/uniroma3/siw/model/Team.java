package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="team")
public class Team {
	
	public final static String UPLOADED_FOLDER_TEAM = "src/main/resources/static/images/teams/";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Nome non deve essere vuoto
	@Column(name = "name", nullable = false)
    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 3, max = 50, message = "Il nome deve avere tra 3 e 50 caratteri")
    private String name;
    
    // L'anno di fondazione deve essere almeno 1800 e non può superare l'anno corrente
	@Column(name = "foundation_year", nullable = false)
    @Min(value = 1800, message = "L'anno di fondazione non può essere precedente al 1800")
    private int foundationYear;
    
    // Indirizzo non deve essere vuoto
	@Column(name = "address", nullable = false)
    @NotBlank(message = "L'indirizzo non può essere vuoto")
    private String address;
    
    // Lista di giocatori associata al team
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();
    
    // Presidente non può essere nullo
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "president_id", nullable = false)
    private President president;
    
    @Column(name = "main_image_path")
    private String mainImagePath;
    
	
	public Team() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFoundationYear() {
		return foundationYear;
	}

	public void setFoundationYear(int foundationYear) {
		this.foundationYear = foundationYear;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public President getPresident() {
		return president;
	}

	public void setPresident(President president) {
		this.president = president;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getMainImagePath() {
		return mainImagePath;
	}

	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}
	
	public void addPlayer(Player player) {
	    if (player != null) {
	        players.add(player);
	        player.setTeam(this); // Imposta il collegamento bidirezionale
	    }
	}
	
	public void removePlayer(Player player) {
	    if (player != null) {
	        players.remove(player);
	        player.setTeam(null); // Rimuove il collegamento bidirezionale
	    }
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, foundationYear, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		return Objects.equals(address, other.address) && foundationYear == other.foundationYear
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	
}
