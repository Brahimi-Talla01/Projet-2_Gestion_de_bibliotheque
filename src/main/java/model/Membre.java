package model;

import java.time.LocalDate;

public class Membre extends Personne {
    private int id;
    private LocalDate adhesionDate;

    public Membre() {
        super();
    }

    public Membre(int id, String nom, String prenom, String email, LocalDate adhesionDate) {
        super(nom, prenom, email);
        this.id = id;
        this.adhesionDate = adhesionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAdhesionDate() {
        return adhesionDate;
    }

    public void setAdhesionDate(LocalDate adhesionDate) {
        this.adhesionDate = adhesionDate;
    }

    // Ajout de la méthode afficherDetails  [cite: 41]
    public void afficherDetails() {
        System.out.println("Membre: " + prenom + " " + nom + " | Email: " + email + " | Adhésion: " + adhesionDate);
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Membre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adhesionDate=" + adhesionDate +
                '}';
    }

}
