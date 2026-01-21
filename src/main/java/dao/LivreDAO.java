package dao;

import config.DBConnection;
import model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    public void ajouter(Livre livre) throws SQLException {
        String sql = "INSERT INTO livres (titre, auteur, categorie, annee_publication, nombre_exemplaires) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getCategorie());
            pstmt.setInt(4, livre.getAnneePublication());
            pstmt.setInt(5, livre.getNombreExemplaire());

            pstmt.executeUpdate();
        }
    }

    public List<Livre> rechercherParTitreOuCategorie(String query) throws  SQLException{
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres WHERE titre ILIKE ? OR categorie ILIKE ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                livres.add(new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getInt("annee_publication"),
                        rs.getInt("nombre_exemplaires")
                ));
            }
        }

        return livres;
    }

    public List<Livre> findAll() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                livres.add(new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getInt("annee_publication"),
                        rs.getInt("nombre_exemplaires")
                ));
            }
        }

        return livres;
    }

    // Recherché un livre par son ID
    public Livre findById(int id) throws SQLException {
        String sql = "SELECT * FROM livres WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Un livre trouvé → on le construit et on le retourne
                    return new Livre(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getString("categorie"),
                            rs.getInt("annee_publication"),
                            rs.getInt("nombre_exemplaires")
                    );
                } else {

                    return null;
                }
            }
        }
    }

    // Mettre à jour un livre
    public void update(Livre livre) throws SQLException {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, categorie = ?, nombre_exemplaires = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Remplissage des paramètres
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getCategorie());
            pstmt.setInt(4, livre.getAnneePublication());
            pstmt.setInt(5, livre.getNombreExemplaire());
            pstmt.setInt(6, livre.getId());

            // Exécuter la mise à jour
            int rowsAffected = pstmt.executeUpdate();

            // Optionnel vérifier si une ligne a été modifiée
            if (rowsAffected == 0) {
                throw new SQLException("Échec de la mise à jour : aucun livre trouvé avec l'ID " + livre.getId());
            }
        }
    }

    // Supprimer un livre
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM livres WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aucun livre trouvé avec l'ID : " + id);
            }
        }
    }
}
