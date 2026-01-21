package controller;

import model.Livre;
import model.Membre;
import service.LivreService;
import service.EmpruntService;
import service.MembreService;

import exception.BookNotFoundException;
import exception.InsufficientStockException;
import exception.MemberNotFoundException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LibraryController {
    private final LivreService livreService;
    private final EmpruntService empruntService;
    private final MembreService membreService;
    private final Scanner scanner;

    public LibraryController() {
        this.livreService = new LivreService();
        this.empruntService = new EmpruntService();
        this.membreService = new MembreService();
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("       SYSTÈME DE GESTION DE BIBLIOTHÈQUE");
            System.out.println("=".repeat(50));
            System.out.println("1. Gestion des Livres");
            System.out.println("2. Gestion des Membres");
            System.out.println("3. Gestion des Emprunts");
            System.out.println("4. Quitter");
            System.out.print("Choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> menuLivres();
                case 2 -> menuMembres();
                case 3 -> menuEmprunts();
                case 4 -> {
                    System.out.println("Au revoir !");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ========== MENU LIVRES ==========
    private void menuLivres() {
        while (true) {
            System.out.println("\n--- GESTION DES LIVRES ---");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Supprimer un livre");
            System.out.println("3. Afficher tous les Livres");
            System.out.println("4. Mettre à jour un Livre");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> menuAjouterLivre();
                case 2 -> menuSupprimerLivre();
                case 3 -> menuAfficherLivres();
                case 4 -> mettreAJourUnLivre();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void menuAjouterLivre() {
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();
        System.out.print("Catégorie : ");
        String cat = scanner.nextLine();
        System.out.print("Année de publication : ");
        int anneePublication = scanner.nextInt();
        System.out.print("Nombre d'exemplaires : ");
        int nbr = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Votre requête est en cours d'exécution...");

        Livre livre = new Livre(0, titre, auteur, cat, anneePublication ,nbr);
        try {
            livreService.ajouter(livre);
            System.out.println("Livre ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur base de données : " + e.getMessage());
        }
    }

    private void menuSupprimerLivre() {
        System.out.print("ID du livre à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            livreService.supprimer(id);
            System.out.println("Livre supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur base de données : " + e.getMessage());
        }
    }

    private void menuAfficherLivres() {
        try {
            System.out.println("Votre requête est en cours d'exécution...");
            List<Livre> livres = livreService.findAll();
            if (livres.isEmpty()) {
                System.out.println("\nAucun livre dans la bibliothèque.");
            } else {
                System.out.println("\nListe des livres :");
                System.out.println("-".repeat(100));
                System.out.printf("%-5s %-30s %-20s %-15s %-8s %-10s%n",
                        "ID", "Titre", "Auteur", "Catégorie", "Année", "Exemplaires");
                System.out.println("-".repeat(100));
                for (Livre l : livres) {
                    System.out.printf("%-5d %-30s %-20s %-15s %-8d %-10d%n",
                            l.getId(),
                            l.getTitre().length() > 28 ? l.getTitre().substring(0, 28) + ".." : l.getTitre(),
                            l.getAuteur(),
                            l.getCategorie(),
                            l.getAnneePublication(),
                            l.getNombreExemplaire());
                }
                System.out.println("-".repeat(100));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    private void mettreAJourUnLivre() {
        System.out.print("ID du livre à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Livre livreExistant = livreService.findById(id);
            if (livreExistant == null) {
                System.out.println("Aucun livre trouvé avec l'ID " + id);
                return;
            }

            System.out.println("Livre actuel : " + livreExistant.getTitre() + " - " + livreExistant.getAuteur());
            System.out.print("Nouveau titre (laisser vide pour ne pas changer) : ");
            String nouveauTitre = scanner.nextLine();
            System.out.print("Nouvel auteur (laisser vide pour ne pas changer) : ");
            String nouvelAuteur = scanner.nextLine();
            System.out.print("Nouvelle catégorie (laisser vide pour ne pas changer) : ");
            String nouvelleCategorie = scanner.nextLine();
            System.out.print("Nouveau nombre d'exemplaires (0 pour ne pas changer) : ");
            int nouveauNb = scanner.nextInt();
            scanner.nextLine();

            // Mettre à jour les champs si nécessaire
            if (!nouveauTitre.trim().isEmpty()) {
                livreExistant.setTitre(nouveauTitre);
            }
            if (!nouvelAuteur.trim().isEmpty()) {
                livreExistant.setAuteur(nouvelAuteur);
            }
            if (!nouvelleCategorie.trim().isEmpty()) {
                livreExistant.setCategorie(nouvelleCategorie);
            }
            if (nouveauNb > 0) {
                livreExistant.setNombreExemplaire(nouveauNb);
            }

            livreService.update(livreExistant);
            System.out.println("✅ Livre mis à jour avec succès !");
        } catch (BookNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Erreur base de données : " + e.getMessage());
        }
    }

    // ========== MENU MEMBRES ==========
    private void menuMembres() {
        while (true) {
            System.out.println("\n--- GESTION DES MEMBRES ---");
            System.out.println("1. Inscrire un membre");
            System.out.println("2. Supprimer un membre");
            System.out.println("3. Rechercher un membre par nom");
            System.out.println("4. Afficher tous les membres");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> menuInscrireMembre();
                case 2 -> menuSupprimerMembre();
                case 3 -> menuRechercherMembre();
                case 4 -> menuAfficherTousLesMembres();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void menuInscrireMembre() {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();

        Membre membre = new Membre(0, nom, prenom, email, LocalDate.now());
        try {
            System.out.println("Votre requête est en cours d'exécution...");
            membreService.inscrire(membre);
            System.out.println("✅ Membre inscrit avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur base de données : " + e.getMessage());
        }
    }

    private void menuSupprimerMembre() {
        System.out.print("ID du membre à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.println("Votre requête est en cours d'exécution...");
            membreService.supprimer(id);
            System.out.println("✅ Membre supprimé avec succès !");
        } catch (MemberNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Erreur base de données : " + e.getMessage());
        }
    }

    private void menuRechercherMembre() {
        System.out.print("Nom (ou partie du nom) : ");
        String nom = scanner.nextLine();

        try {
            System.out.println("Votre requête est en cours d'exécution...");
            List<Membre> membres = membreService.rechercherParNom(nom);
            if (membres.isEmpty()) {
                System.out.println("❌ Aucun membre trouvé.");
            } else {
                System.out.println("\nMembres trouvés :");
                for (Membre m : membres) {
                    System.out.println(" - ID: " + m.getId() + ", Nom: " + m.getNom() + " " + m.getPrenom() + ", Email: " + m.getEmail());
                }
            }
        } catch (MemberNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur base de données : " + e.getMessage());
        }
    }

    private void menuAfficherTousLesMembres() {
        try {
            List<Membre> membres = membreService.findAll();
            if (membres.isEmpty()) {
                System.out.println("\nAucun membre inscrit.");
            } else {
                System.out.println("\nListe de tous les membres :");
                System.out.println("-".repeat(80));
                System.out.printf("%-5s %-15s %-15s %-25s %-12s%n",
                        "ID", "Nom", "Prénom", "Email", "Adhésion");
                System.out.println("-".repeat(80));
                for (Membre m : membres) {
                    System.out.printf("%-5d %-15s %-15s %-25s %-12s%n",
                            m.getId(),
                            m.getNom(),
                            m.getPrenom(),
                            m.getEmail(),
                            m.getAdhesionDate());
                }
                System.out.println("-".repeat(80));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres : " + e.getMessage());
        } catch (MemberNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ========== MENU EMPRUNTS ==========
    private void menuEmprunts() {
        while (true) {
            System.out.println("\n--- GESTION DES EMPRUNTS ---");
            System.out.println("1. Effectuer un emprunt");
            System.out.println("2. Retourner un livre");
            System.out.println("3. Retour au menu principal");
            System.out.print("Choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> menuEmprunter();
                case 2 -> menuRetourner();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void menuEmprunter() {
        System.out.print("ID du Membre : ");
        int mid = scanner.nextInt();
        System.out.print("ID du Livre : ");
        int lid = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.println("Votre requête est en cours d'exécution...");
            empruntService.effectuerEmprunt(mid, lid);
            System.out.println("Emprunt enregistré !");
        } catch (BookNotFoundException e) {
            System.out.println("Livre non trouvé : " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("Stock insuffisant : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur technique : " + e.getMessage());
        }
    }

    private void menuRetourner() {
        System.out.print("ID de l'Emprunt : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.println("Votre requête est en cours d'exécution...");
            long penalite = empruntService.retournerLivre(id);
            if (penalite > 0) {
                System.out.println("Livre rendu avec RETARD ! Pénalité à payer : " + penalite + " F CFA.");
            } else {
                System.out.println("Livre rendu à temps. Merci !");
            }
        } catch (BookNotFoundException e) {
            System.out.println("Livre non trouvé : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur technique : " + e.getMessage());
        }
    }
}