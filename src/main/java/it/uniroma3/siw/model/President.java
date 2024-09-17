package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "president")
public class President {
	
	private final static String DEFAULT_USER_IMAGE = "/images/defaultimage.png"; 
    @SuppressWarnings("unused")
	public final static String UPLOADED_FOLDER_PROFILEPICS = "src/main/resources/static/images/profilePics/";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "president_id_seq")
    @SequenceGenerator(name = "president_id_seq", sequenceName = "president_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "name")
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@Column(name = "surname")
	@NotBlank(message = "Surname is mandatory")
	private String surname;
	
	@Column(name = "codice_fiscale")
	@NotBlank(message = "Code is mandatory")
	private String codiceFiscale;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	
	private String birthDate;
	
	@Column(name = "birth_place")
	private String birthPlace;
	
	
	private String profilePic;
	
	private String bio;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Team team;
	
	 @OneToOne(mappedBy = "president", cascade = CascadeType.ALL)
	 private Credentials credentials;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
}
