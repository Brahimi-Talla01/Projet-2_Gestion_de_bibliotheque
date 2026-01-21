package exception;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String titre) {
        super("Stock épuisé pour le livre : " + titre + ". Emprunt impossible.");
    }
}