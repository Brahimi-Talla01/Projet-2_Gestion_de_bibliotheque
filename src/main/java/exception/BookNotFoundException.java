package exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(int id) {
        super("Le livre avec l'ID " + id + " est introuvable dans la bibliothèque.");
    }

    public BookNotFoundException(String titre) {
        super("Aucun livre ne correspond au titre : " + titre);
    }
}