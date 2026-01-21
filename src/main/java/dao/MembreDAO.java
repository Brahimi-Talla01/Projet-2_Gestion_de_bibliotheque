package dao;

import config.DBConnection;
import exception.MemberNotFoundException;
import model.Livre;
import model.Membre;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    public void inscrire(Membre membre) throws SQLException {
        String sql = "INSERT INTO membres (nom, prenom, email, adhesion_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, membre.getNom());
            pstmt.setString(2, membre.getPrenom());
            pstmt.setString(3, membre.getEmail());
            pstmt.setDate(4, Date.valueOf(membre.getAdhesionDate()));

            pstmt.executeUpdate();
        }
    }

    public List<Membre> rechercherParNom(String nom) throws SQLException, MemberNotFoundException {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membres WHERE nom ILIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nom + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                membres.add(new Membre(
                        rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("email"), rs.getDate("adhesion_date").toLocalDate()
                ));
            }
        }
        return membres;
    }

    // Supprimer un membre
    public void delete(int membreId) throws SQLException {
        String sql = "DELETE FROM membres WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, membreId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aucun membre trouvé avec l'ID : " + membreId);
            }
        }
    }

    // Rechercher un membre par son ID
    public Membre findById(int id) throws SQLException, MemberNotFoundException {
        String sql = "SELECT * FROM membres WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Conversion
                    java.sql.Date sqlDate = rs.getDate("adhesionDate");
                    LocalDate adhesionDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    return new Membre(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            adhesionDate
                    );
                } else {
                    return null;
                }
            }
        }
    }

    // Afficher tout les membres
    public List<Membre> findAll() throws SQLException {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membres ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getDate("adhesion_date").toLocalDate()
                );
                membres.add(membre);
            }
        }
        return membres;
    }

}