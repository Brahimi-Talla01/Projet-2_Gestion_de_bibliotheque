package dao;

import config.DBConnection;
import model.Emprunt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {

    // Enregistrer un emprunt
    public void enregistrerEmprunt(Emprunt emprunt) throws SQLException {
        String sql = "INSERT INTO emprunts (membre_id, livre_id, date_emprunt, date_retour_prevue) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, emprunt.getMembreId());
            pstmt.setInt(2, emprunt.getLivreId());
            pstmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            pstmt.setDate(4, Date.valueOf(emprunt.getDateRetourPrevue()));

            pstmt.executeUpdate();

            // Note : Idéalement, il faudrait ici décréter le stock du livre via LivreDAO
        }
    }

    // Gérer le retour d'un livre
    public void enregistrerRetour(int idEmprunt) throws SQLException {
        String sql = "UPDATE emprunts SET date_retour_effective = CURRENT_DATE WHERE id_emprunt = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmprunt);
            pstmt.executeUpdate();
        }
    }

    // Récupérer un emprunt spécifique (pour calculer la pénalité après coup)
    public Emprunt findById(int idEmprunt) throws SQLException {
        String sql = "SELECT * FROM emprunts WHERE id_emprunt = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmprunt);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Emprunt e = new Emprunt();
                e.setIdEmprunt(rs.getInt("id_emprunt"));
                e.setMembreId(rs.getInt("membre_id"));
                e.setLivreId(rs.getInt("livre_id"));
                e.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                e.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());

                Date retourEff = rs.getDate("date_retour_effective");
                if (retourEff != null) {
                    e.setDateRetourEffective(retourEff.toLocalDate());
                }
                return e;
            }
        }
        return null;
    }

    // Lister les emprunts en retard
    public List<Emprunt> findEmpruntsEnRetard() throws SQLException {
        List<Emprunt> retards = new ArrayList<>();
        String sql = "SELECT * FROM emprunts WHERE date_retour_effective IS NULL AND date_retour_prevue < CURRENT_DATE";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprunt e = new Emprunt();
                e.setIdEmprunt(rs.getInt("id_emprunt"));
                e.setMembreId(rs.getInt("membre_id"));
                e.setLivreId(rs.getInt("livre_id"));
                e.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                e.setDateRetourPrevue(rs.getDate("date_retour_prevue").toLocalDate());
                retards.add(e);
            }
        }
        return retards;
    }
}