package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "player")
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Column(name = "name")
	private String name;
	
    @Column(name = "surname")
	private String surname;

	private String birthDate;
	
	private String birthPlace;
	
	private String role;
	
	private String contractStartDate;
	
	private String contractFinishDate;
	
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractFinishDate() {
		return contractFinishDate;
	}

	public void setContractFinishDate(String contractFinishDate) {
		this.contractFinishDate = contractFinishDate;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, birthPlace, contractFinishDate, contractStartDate, id, name, role, surname,
				team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(birthPlace, other.birthPlace)
				&& Objects.equals(contractFinishDate, other.contractFinishDate)
				&& Objects.equals(contractStartDate, other.contractStartDate) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(role, other.role)
				&& Objects.equals(surname, other.surname) && Objects.equals(team, other.team);
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", birthDate=" + birthDate
				+ ", birthPlace=" + birthPlace + ", role=" + role + ", contractStartDate=" + contractStartDate
				+ ", contractFinishDate=" + contractFinishDate + ", team=" + team + "]";
	}
	
	
	
}
