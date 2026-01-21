-- 1. Création de la table des Livres
CREATE TABLE livres (
    id SERIAL PRIMARY KEY, 
    titre VARCHAR(255) NOT NULL, 
    auteur VARCHAR(255) NOT NULL, 
    categorie VARCHAR(100), 
    annee_publication INT, 
    nombre_exemplaires INT DEFAULT 1 
);

-- 2. Création de la table des Membres
CREATE TABLE membres (
    id SERIAL PRIMARY KEY, 
    nom VARCHAR(100) NOT NULL, 
    prenom VARCHAR(100) NOT NULL, 
    email VARCHAR(255) UNIQUE, 
    adhesion_date DATE DEFAULT CURRENT_DATE 
);

-- 3. Création de la table des Emprunts
CREATE TABLE emprunts (
    id_emprunt SERIAL PRIMARY KEY, 
    membre_id INT NOT NULL, 
    livre_id INT NOT NULL, 
    date_emprunt DATE DEFAULT CURRENT_DATE, 
    date_retour_prevue DATE NOT NULL, 
    date_retour_effective DATE, 
    

    CONSTRAINT fk_membre FOREIGN KEY (membre_id) REFERENCES membres(id) ON DELETE CASCADE,
    CONSTRAINT fk_livre FOREIGN KEY (livre_id) REFERENCES livres(id) ON DELETE CASCADE
);