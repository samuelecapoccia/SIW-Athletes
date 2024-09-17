package it.uniroma3.siw.usersDataLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.TeamRepository;
import it.uniroma3.siw.repository.PlayerRepository;

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Creazione dell'amministratore
        Credentials adminCredentials = new Credentials();
        adminCredentials.setEmail("admin@root.com");
        adminCredentials.setPassword(passwordEncoder.encode("pass"));
        adminCredentials.setRole(Credentials.ADMIN_ROLE);
        credentialsRepository.save(adminCredentials);
        
        // Creazione presidenti e squadre
        Team juventus = createPresidentAndTeam("Gianluca", "Ferrero", "CF123456789", "07/12/1963", "Torino", 
        		"/images/profilePics/ferrero.jpg", "Juventus", 1897, "Via Druento 175", "/images/teams/juventus.png", 
                "president1@gmail.com", "pass");
        Team napoli = createPresidentAndTeam("Aurelio", "De Laurentiis", "CF987654321", "24/05/1949", "Roma", 
        		"/images/profilePics/delaurentiis.jpg", "Napoli", 1926, "Via del Maio di Porto 9 ", "/images/teams/napoli.png", 
                "president2@gmail.com", "pass");
        Team roma = createPresidentAndTeam("Dan", "Friedkin", "CF456789123", "23/03/1965", "Houston", 
        		"/images/profilePics/friedkin.jpg", "Roma", 1927, "Piazzale Dino Viola 1", "/images/teams/roma.png", 
                "president3@gmail.com", "pass");
        Team milan = createPresidentAndTeam("Paolo", "Scaroni", "CF456123789", "28/12/1946", "Vicenza", 
        		"/images/profilePics/scaroni.jpg", "Milan", 1899, "Via Aldo Rossi 8", "/images/teams/milan.png", 
                "president4@gmail.com", "pass");
        Team inter = createPresidentAndTeam("Steven", "Zhang", "CF789123456", "20/12/1991", "Nanchino", 
        		"/images/profilePics/zhang.jpg", "Inter", 1908, "Viale della Liberazione 16/18", "/images/teams/inter.png", 
                "president5@gmail.com", "pass");
        Team lazio = createPresidentAndTeam("Claudio", "Lotito", "CF321654987", "09/05/1957", "Roma", 
        		"/images/profilePics/lotito.jpg", "Lazio", 1900, "Via di Santa Cornelia 1000", "/images/teams/lazio.png",
        		"president6@gmail.com", "pass");

     // Aggiungi giocatori specifici alle squadre
        addPlayerToTeam(juventus, "Dusan", "Vlahovic", "28/01/2000", "Batocina", "Attaccante", 
                "01/01/2022", "30/06/2027");
        addPlayerToTeam(juventus, "Kenan", "Yildiz", "08/01/2003", "Bad Cannstatt", "Centrocampista", 
                "01/01/2022", "30/06/2027");
        addPlayerToTeam(juventus, "Teun", "Koopmeiners", "17/01/1998", "Gorinchem", "Centrocampista", 
                "01/01/2023", "30/06/2026");
        addPlayerToTeam(napoli, "Romelu", "Lukaku", "13/05/1993", "Antwerp", "Attaccante", 
                "01/09/2021", "30/06/2024");
        addPlayerToTeam(napoli, "Khvicha", "Kvaratskhelia", "12/02/2001", "Batumi", "Attaccante", 
                "01/07/2022", "30/06/2027");
        addPlayerToTeam(napoli, "Alessandro", "Buongiorno", "30/01/1998", "Torino", "Difensore", 
                "01/09/2021", "30/06/2024");
        addPlayerToTeam(milan, "Álvaro", "Morata", "23/10/1992", "Madrid", "Attaccante", 
                "01/08/2022", "30/06/2025");
        addPlayerToTeam(milan, "Christian", "Pulisic", "18/09/1998", "Hershey", "Attaccante", 
                "01/08/2022", "30/06/2025");
        addPlayerToTeam(milan, "Rafael", "Leao", "10/06/1999", "Melo", "Attaccante", 
                "01/08/2022", "30/06/2025");
        addPlayerToTeam(inter, "Lautaro", "Martinez", "22/08/1997", "Bahía Blanca", "Attaccante", 
                "01/07/2018", "30/06/2026");
        addPlayerToTeam(inter, "Marcus", "Thuram", "06/08/1997", "Parma", "Attaccante", 
                "01/07/2022", "30/06/2027");
        addPlayerToTeam(inter, "Hakan", "Calhanoglu", "08/02/1994", "Mardin", "Centrocampista", 
                "01/07/2021", "30/06/2026");
        addPlayerToTeam(lazio, "Taty", "Castellanos", "18/10/1998", "Gualeguay", "Attaccante", 
                "01/07/2022", "30/06/2025");
        addPlayerToTeam(lazio, "Ivan", "Provedel", "21/05/1994", "Pordenone", "Portiere", 
                "01/07/2022", "30/06/2025");
        addPlayerToTeam(lazio, "Alessio", "Romagnoli", "12/01/1995", "Anzio", "Difensore", 
                "01/07/2022", "30/06/2025");
        addPlayerToTeam(roma, "Artem", "Dovbyk", "21/09/1997", "Dnipro", "Attaccante", 
                "01/01/2023", "30/06/2026");
        addPlayerToTeam(roma, "Paulo", "Dybala", "15/11/1993", "Laguna Larga", "Attaccante", 
                "01/01/2023", "30/06/2026");
        addPlayerToTeam(roma, "Gianluca", "Mancini", "17/04/1996", "Pontedera", "Difensore", 
                "01/01/2023", "30/06/2026");

        // Creazione presidente senza squadra
        createPresidentWithoutTeam("Mario", "Rossi", "CF000000000", "01/01/1960", "Milano", 
                "president7@gmail.com", "pass");

        System.out.println("Users and teams data loaded.");
    }

    // Metodo per creare un presidente e una squadra
    private Team createPresidentAndTeam(String presName, String presSurname, String codiceFiscale, 
            String birthDate, String birthPlace, String profilePic, String teamName, 
            int foundationYear, String address, String imagePath, 
            String email, String password) {

        // Crea il presidente
        President president = new President();
        president.setName(presName);
        president.setSurname(presSurname);
        president.setCodiceFiscale(codiceFiscale);
        president.setBirthDate(birthDate);
        president.setBirthPlace(birthPlace);

        // Crea la squadra e la associa al presidente
        Team team = new Team();
        team.setName(teamName);
        team.setFoundationYear(foundationYear);
        team.setAddress(address);
        team.setMainImagePath(imagePath);
        team.setPresident(president);  // Associa il presidente al team
        president.setTeam(team);  // Associa il team al presidente

        // Crea le credenziali del presidente
        Credentials presidentCredentials = new Credentials();
        presidentCredentials.setEmail(email);
        presidentCredentials.setPassword(passwordEncoder.encode(password));
        presidentCredentials.setRole(Credentials.PRESIDENT_ROLE);
        presidentCredentials.setPresident(president);  // Associa il presidente alle credenziali

        // Salva le credenziali e il team
        credentialsRepository.save(presidentCredentials);
        teamRepository.save(team);  // Il presidente viene salvato con il team

        return team;  // Restituisci la squadra per aggiungere i giocatori
    }

    // Metodo per aggiungere un giocatore a una squadra
    private void addPlayerToTeam(Team team, String name, String surname, String birthDate, 
            String birthPlace, String role, String contractStartDate, String contractFinishDate) {

        Player player = new Player();
        player.setName(name);
        player.setSurname(surname);
        player.setBirthDate(birthDate);
        player.setBirthPlace(birthPlace);
        player.setRole(role);
        player.setContractStartDate(contractStartDate);
        player.setContractFinishDate(contractFinishDate);
        player.setTeam(team);  // Associa il giocatore alla squadra

        playerRepository.save(player);  // Salva il giocatore
    }

    // Metodo per creare un presidente senza una squadra
    private void createPresidentWithoutTeam(String presName, String presSurname, String codiceFiscale, 
            String birthDate, String birthPlace, String email, String password) {

        // Crea il presidente senza squadra
        President president = new President();
        president.setName(presName);
        president.setSurname(presSurname);
        president.setCodiceFiscale(codiceFiscale);
        president.setBirthDate(birthDate);
        president.setBirthPlace(birthPlace);

        // Crea le credenziali del presidente
        Credentials presidentCredentials = new Credentials();
        presidentCredentials.setEmail(email);
        presidentCredentials.setPassword(passwordEncoder.encode(password));
        presidentCredentials.setRole(Credentials.PRESIDENT_ROLE);
        presidentCredentials.setPresident(president);  // Associa il presidente alle credenziali

        // Salva le credenziali (il presidente non è associato a nessuna squadra)
        credentialsRepository.save(presidentCredentials);
    }
}