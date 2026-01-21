package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    /*
        psql 'postgresql://neondb_owner:npg_oGDd0hBKHa2b@ep-royal-forest-ahnd6mis-pooler.c-3.us-east-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require'
    */

    // Nom du fichier dans le dossier 'resources'
    private static final String CONFIG_FILE = "db.properties";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {

            // Si la connexion existe déjà et est encore ouverte, on la réutilise
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            // Chargement du fichier de configuration
            Properties props = new Properties();
            InputStream is = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_FILE);

            if (is == null) {
                throw new RuntimeException("Fichier " + CONFIG_FILE + " introuvable dans resources");
            }
            props.load(is);

            // Récupération des paramètres
            String url = props.getProperty("db.url");
            String utilisateur = props.getProperty("db.user");
            String motDePasse = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            // Chargement du driver PostgreSQL
            Class.forName(driver);

            // Connexion
            System.out.println("Connexon en cours avec la BD...");
            connection = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussie à PostgreSQL.");

            return connection;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver PostgreSQL non trouvé", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL lors de la connexion", e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de configuration", e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion PostgreSQL fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}