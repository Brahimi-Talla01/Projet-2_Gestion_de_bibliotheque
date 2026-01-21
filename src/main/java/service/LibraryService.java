package service;

import dao.EmpruntDAO;
import dao.LivreDAO;
import dao.MembreDAO;
import exception.BookNotFoundException;
import exception.InsufficientStockException;
import exception.MemberNotFoundException;
import model.Emprunt;
import model.Livre;
import model.Membre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LibraryService {
    private final LivreDAO livreDAO;
    private final MembreDAO membreDAO;
    private final EmpruntDAO empruntDAO;

    public LibraryService() {
        this.livreDAO = new LivreDAO();
        this.empruntDAO = new EmpruntDAO();
        this.membreDAO = new MembreDAO();
    }

    // Ajouter un livre
    public void ajouterNouveauLivre(Livre livre) throws SQLException {
        livreDAO.ajouter(livre);
    }

    // Supprimer un livre
    public void supprimerLivre(int livreId) throws SQLException {
        livreDAO.delete(livreId);
    }

    // Logique emprunts
    public void effectuerEmprunt(int membreId, int livreId) throws SQLException, BookNotFoundException, InsufficientStockException {

        // Vérification si le livre existe
        Livre livre = livreDAO.findById(livreId);
        if (livre == null) throw new BookNotFoundException(livreId);
        if (livre.getNombreExemplaire() <= 0) throw new InsufficientStockException(livre.getTitre());

        // Création d'un emprunt
        Emprunt emprunt = new Emprunt();
        emprunt.setMembreId(membreId);
        emprunt.setLivreId(livreId);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));

        // Enregistrer un emprunt en base
        empruntDAO.enregistrerEmprunt(emprunt);

        // Mise à jour du stock de livre
        livre.setNombreExemplaire(livre.getNombreExemplaire() - 1);
        livreDAO.update(livre);
    }

    public long retournerLivre(int idEmprunt) throws SQLException, BookNotFoundException {
        // Enregistrer le retour dans la table 'emprunts'
        empruntDAO.enregistrerRetour(idEmprunt);

        // Récupérer l'emprunt mis à jour pour obtenir l'ID du livre et les dates
        Emprunt emprunt = empruntDAO.findById(idEmprunt);
        if (emprunt == null) {
            throw new SQLException("Emprunt introuvable pour l'ID : " + idEmprunt);
        }

        // Calculer la pénalité (100 F CFA par jour de retard)
        long penalite = emprunt.calculerPenalites();

        // Remettre le livre en stock
        Livre livre = livreDAO.findById(emprunt.getLivreId());
        if (livre == null) {
            throw new BookNotFoundException(emprunt.getLivreId());
        }

        // On augmente le stock de 1
        livre.setNombreExemplaire(livre.getNombreExemplaire() + 1);

        // On met à jour le livre en base de données
        livreDAO.update(livre);

        return penalite;
    }

    // Logique membres
    public void ajouterMembre(Membre membre) {
        try {
            membreDAO.inscrire(membre);
        } catch (SQLException e){
            System.out.println("Error : "+ e.getMessage());
        }
    }

    // Supprimer un membre
    public void delete(int id) throws SQLException, MemberNotFoundException {
        if (membreDAO.findById(id) == null) {
            throw new MemberNotFoundException(id);
        }
        membreDAO.delete(id);
    }

    // Rechercher un membre
    public List<Membre> searchMembers(String name) throws SQLException, MemberNotFoundException {
        List<Membre> membres = membreDAO.rechercherParNom(name);

        if (membres.isEmpty()) {
            throw new MemberNotFoundException(name);
        }
        return membres;
    }

}
