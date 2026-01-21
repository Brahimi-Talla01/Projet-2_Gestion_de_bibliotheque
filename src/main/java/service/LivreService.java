package service;

import dao.LivreDAO;
import exception.BookNotFoundException;
import model.Livre;

import java.sql.SQLException;
import java.util.List;

public class LivreService {
    private final LivreDAO livreDAO;

    public LivreService() {
        this.livreDAO = new LivreDAO();
    }

    public void ajouter(Livre livre) throws SQLException {
        livreDAO.ajouter(livre);
    }

    public void supprimer(int livreId) throws SQLException {
        livreDAO.delete(livreId);
    }

    public Livre findById(int id) throws SQLException, BookNotFoundException {
        Livre livre = livreDAO.findById(id);
        if (livre == null) {
            throw new BookNotFoundException(id);
        }
        return livre;
    }

    public List<Livre> findAll() throws SQLException {
        return livreDAO.findAll();
    }

    public void update(Livre livre) throws SQLException {
        livreDAO.update(livre);
    }
}