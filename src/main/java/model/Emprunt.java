package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprunt {
    private int idEmprunt;
    private int membreId;
    private int livreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    public Emprunt() {}

    // Méthode pour calculer les pénalités
    public long calculerPenalites() {

        // Si le livre n'est pas encore rendu, on calcule par rapport à aujourd'hui
        LocalDate dateFin = (dateRetourEffective != null) ? dateRetourEffective : LocalDate.now();

        if (dateFin.isAfter(dateRetourPrevue)) {
            long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevue, dateFin);
            return joursRetard * 100;
        }
        return 0;
    }

    // Getters et Setters pour tous les attributs...
    public int getIdEmprunt() { return idEmprunt; }
    public void setIdEmprunt(int idEmprunt) { this.idEmprunt = idEmprunt; }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Emprunt{" +
                "idEmprunt=" + idEmprunt +
                ", membreId=" + membreId +
                ", livreId=" + livreId +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourEffective=" + dateRetourEffective +
                '}';
    }
}