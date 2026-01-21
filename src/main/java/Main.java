import config.DBConnection;
import controller.LibraryController;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        //Test de la connexion
        Connection con = DBConnection.getConnection();

        if (con != null){
            System.out.println("Connexion Ok!");
        } else {
            System.out.println("Échec de la connexion.");
        }

        System.out.println("Démarrage de l'application...");
        LibraryController controller = new LibraryController();
        controller.afficherMenuPrincipal();
    }
}