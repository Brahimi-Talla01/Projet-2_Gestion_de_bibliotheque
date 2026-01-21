package model;

public class Livre {
    private int id;
    private String titre;
    private  String auteur;
    private String categorie;
    private int anneePublication;
    private int nombreExemplaire;

    public Livre(){}

    public Livre(int id, String titre, String auteur, String categorie, int anneePublication ,int nombreExemplaire) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.anneePublication = anneePublication;
        this.nombreExemplaire = nombreExemplaire;
    }

    public Livre(String titre, String auteur, String categorie, int anneePublication, int nombreExemplaire) {
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.anneePublication = anneePublication;
        this.nombreExemplaire = nombreExemplaire;
    }


    public void setTitre(String titre){
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getNombreExemplaire() {
        return nombreExemplaire;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public void setNombreExemplaire(int nombreExemplaire) {
        this.nombreExemplaire = nombreExemplaire;
    }

    public void afficherDetails() {
        System.out.println("Livre: " + titre + " | Auteur: " + auteur + " | Catégorie: " + categorie + " | Stock: " + nombreExemplaire);
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", nombreExemplaire='" + nombreExemplaire + '\'' +
                '}';
    }
}
