package service;

import dao.MembreDAO;
import exception.MemberNotFoundException;
import model.Membre;

import java.sql.SQLException;
import java.util.List;

public class MembreService {
    private final MembreDAO membreDAO;

    public MembreService() {
        this.membreDAO = new MembreDAO();
    }

    public void inscrire(Membre membre) throws SQLException {
        membreDAO.inscrire(membre);
    }

    public void supprimer(int id) throws SQLException, MemberNotFoundException {
        if (membreDAO.findById(id) == null) {
            throw new MemberNotFoundException(id);
        }
        membreDAO.delete(id);
    }

    public List<Membre> rechercherParNom(String nom) throws SQLException, MemberNotFoundException {
        List<Membre> membres = membreDAO.rechercherParNom(nom);
        if (membres.isEmpty()) {
            throw new MemberNotFoundException("Aucun membre trouvé avec le nom : " + nom);
        }
        return membres;
    }

    public Membre findById(int id) throws SQLException, MemberNotFoundException {
        return membreDAO.findById(id);
    }

    public List<Membre> findAll() throws SQLException, MemberNotFoundException {
        return membreDAO.findAll();
    }


}