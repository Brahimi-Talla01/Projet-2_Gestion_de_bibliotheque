# Projet - 2: Système de Gestion de Bibliothèque (Java/PostgreSQL/Maven)

## Présentation du Projet
Ce projet est une application de gestion de bibliothèque exécutable en console. Elle permet de gérer un inventaire de livres, les inscriptions de membres, ainsi que le cycle complet des emprunts et des retours avec calcul automatique des pénalités de retard.

L'architecture repose sur les principes **SOLID**, le pattern **MVC (Modèle-Vue-Contrôleur)** et le pattern **DAO (Data Access Object)** pour une séparation nette entre la logique métier, l'accès aux données et l'interface utilisateur.

## Technologies Utilisées
* **Langage :** Java 17+
* **Base de données :** PostgreSQL
* **Gestionnaire de dépendances :** Maven
* **Architecture :** MVC + DAO + Service Layer
* **Concepts POO :** Héritage (Personne -> Membre), Encapsulation, Polymorphisme.

## Structure du Projet
```text
src/main/java/
├── config/       # Connexion Singleton à PostgreSQL
├── model/        # Entités (Livre, Membre, Emprunt, Personne)
├── dao/          # Couche d'accès aux données (SQL)
├── service/      # Logique métier et calcul des pénalités
├── exception/    # Exceptions personnalisées
├── controller/   # Gestion des menus et entrées utilisateur
└── Main.java     # Point d'entrée

```

## Installation et Lancement

### 1. Configuration de la Base de Données

1. Créez une base de données nommée `bibliotheque_db` dans PostgreSQL.
2. Exécutez le script SQL fourni (dans le dossier `resources/schema.sql`) pour créer les tables `livres`, `membres` et `emprunts`.


### 2. Configuration du projet

Modifiez le fichier `src/main/resources/db.properties` avec vos identifiants :

```properties
db.url=jdbc:postgresql://localhost:5432/bibliotheque_db
db.user=votre_utilisateur
db.password=votre_mot_de_passe

```
## NB: Le projet possède également une base de données sur Néon donc vous pouvez ne pas créer de Base de données locale.

### 3. Compilation avec Maven

Ouvrez un terminal à la racine du projet et exécutez :

```bash
mvn clean install

```

### 4. Exécution

Lancez l'application via votre IDE ou en ligne de commande :

```bash
mvn exec:java -Dexec.mainClass="Main"

```

## Fonctionnalités implémentées

* [x] **Gestion des Livres :** Ajout, recherche par titre/catégorie, mise à jour des stocks.
* [x] **Gestion des Membres :** Inscription, recherche par nom, suppression.
* [x] **Emprunts & Retours :** Enregistrement des flux, vérification automatique du stock.
* [x] **Pénalités :** Calcul automatique de 100 F CFA par jour de retard lors du retour.
* [x] **Exceptions :** Gestion propre des erreurs (Stock épuisé, ID introuvable).

## Diagramme UML

Le diagramme de classes complet détaillant les relations (0-N entre Membre/Livre et Emprunt) est disponible dans le dossier `resources/classes_UML.pdf`.

## Auteur

* **Talla Ibrahim** - https://github.com/Brahimi-Talla01

```