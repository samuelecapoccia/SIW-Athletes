package it.uniroma3.siw.model;

public class RegistrationForm {

    private Credentials credentials;
    private President president;

    // Getters e setters

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public President getPresident() {
        return president;
    }

    public void setPresident(President president) {
        this.president = president;
    }
}