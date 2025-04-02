-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 02 avr. 2025 à 08:19
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `recrutement`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateurs`
--

DROP TABLE IF EXISTS `administrateurs`;
CREATE TABLE IF NOT EXISTS `administrateurs` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `nom_admin` varchar(255) NOT NULL,
  `prenom_admin` varchar(255) NOT NULL,
  `e_mail_admin` varchar(255) NOT NULL,
  `pw_admin` varchar(255) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `administrateurs`
--

INSERT INTO `administrateurs` (`id_admin`, `nom_admin`, `prenom_admin`, `e_mail_admin`, `pw_admin`) VALUES
(1, 'Durand', 'Alice', 'alice.durand@admin.com', 'admin123'),
(2, 'Bernard', 'Lucas', 'lucas.bernard@admin.com', 'secureadmin');

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

DROP TABLE IF EXISTS `annonce`;
CREATE TABLE IF NOT EXISTS `annonce` (
  `id_annonce` int NOT NULL AUTO_INCREMENT,
  `titre_annonce` varchar(255) NOT NULL,
  `description_annonce` varchar(255) NOT NULL,
  `experience_requise_annonce` varchar(255) NOT NULL,
  `salaire_annonce` int NOT NULL,
  `date_debut_annonce` date NOT NULL,
  `statut_annonce` varchar(255) NOT NULL,
  `lieu_travail_annonce` varchar(255) NOT NULL,
  `type_contrat_annonce` varchar(255) NOT NULL,
  `id_admin` int DEFAULT NULL,
  `id_societe` int DEFAULT NULL,
  `id_categorie` int DEFAULT NULL,
  PRIMARY KEY (`id_annonce`),
  KEY `Annonce_Administrateurs_FK` (`id_admin`),
  KEY `Annonce_Societe0_FK` (`id_societe`),
  KEY `Annonce_Categorie1_FK` (`id_categorie`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `annonce`
--

INSERT INTO `annonce` (`id_annonce`, `titre_annonce`, `description_annonce`, `experience_requise_annonce`, `salaire_annonce`, `date_debut_annonce`, `statut_annonce`, `lieu_travail_annonce`, `type_contrat_annonce`, `id_admin`, `id_societe`, `id_categorie`) VALUES
(1, 'Développeur Full Stack', 'Recherche développeur full stack expérimenté', '3 ans', 45000, '2025-05-01', 'Ouvert', 'Paris', 'CDI', 1, 1, 1),
(2, 'Responsable Marketing', 'Poste de responsable marketing pour entreprise en pleine croissance', '5 ans', 55000, '2025-06-01', 'Ouvert', 'Lyon', 'CDI', 2, 2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `candidature`
--

DROP TABLE IF EXISTS `candidature`;
CREATE TABLE IF NOT EXISTS `candidature` (
  `id_annonce` int NOT NULL,
  `id_demandeurs` int NOT NULL,
  `date_candidature` date NOT NULL,
  `statut_candidature` varchar(255) NOT NULL,
  `note_candidature` int NOT NULL,
  `documents_candidature` varchar(255) NOT NULL,
  PRIMARY KEY (`id_annonce`,`id_demandeurs`),
  KEY `Candidature_Demandeurs0_FK` (`id_demandeurs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `candidature`
--

INSERT INTO `candidature` (`id_annonce`, `id_demandeurs`, `date_candidature`, `statut_candidature`, `note_candidature`, `documents_candidature`) VALUES
(1, 1, '2025-04-01', 'En cours', 85, 'lettre_motivation_jean.pdf'),
(1, 3, '2025-04-03', 'Accepté', 90, 'lettre_motivation_paul.pdf'),
(2, 2, '2025-04-02', 'Rejeté', 70, 'lettre_motivation_sophie.pdf');

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `id_categorie` int NOT NULL AUTO_INCREMENT,
  `nom_categorie` varchar(255) NOT NULL,
  `dexcription_categorie` varchar(255) NOT NULL,
  PRIMARY KEY (`id_categorie`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id_categorie`, `nom_categorie`, `dexcription_categorie`) VALUES
(1, 'Développement Web', 'Toutes les offres liées au développement web'),
(2, 'Marketing', 'Offres pour les spécialistes du marketing');

-- --------------------------------------------------------

--
-- Structure de la table `demandeurs`
--

DROP TABLE IF EXISTS `demandeurs`;
CREATE TABLE IF NOT EXISTS `demandeurs` (
  `id_demandeurs` int NOT NULL AUTO_INCREMENT,
  `nom_demandeur` varchar(255) NOT NULL,
  `prenom_demandeur` varchar(255) NOT NULL,
  `age_demandeur` int NOT NULL,
  `e_mail_demandeur` varchar(255) NOT NULL,
  `adresse_demandeur` varchar(255) NOT NULL,
  `experience_demandeur` varchar(255) NOT NULL,
  `cv_demandeur` varchar(255) NOT NULL,
  `pw_demandeur` varchar(255) NOT NULL,
  PRIMARY KEY (`id_demandeurs`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `demandeurs`
--

INSERT INTO `demandeurs` (`id_demandeurs`, `nom_demandeur`, `prenom_demandeur`, `age_demandeur`, `e_mail_demandeur`, `adresse_demandeur`, `experience_demandeur`, `cv_demandeur`, `pw_demandeur`) VALUES
(1, 'Dupont', 'Jean', 30, 'jean.dupont@email.com', '123 Rue de Paris', '5 ans', 'cv_jean.pdf', 'password123'),
(2, 'Martin', 'Sophie', 28, 'sophie.martin@email.com', '456 Rue de Lyon', '3 ans', 'cv_sophie.pdf', 'securepass'),
(3, 'Lemoine', 'Paul', 35, 'paul.lemoine@email.com', '789 Rue de Marseille', '10 ans', 'cv_paul.pdf', 'paulpass');

-- --------------------------------------------------------

--
-- Structure de la table `societe`
--

DROP TABLE IF EXISTS `societe`;
CREATE TABLE IF NOT EXISTS `societe` (
  `id_societe` int NOT NULL AUTO_INCREMENT,
  `nom_societe` varchar(255) NOT NULL,
  `secteur_activite_societe` varchar(255) NOT NULL,
  `adresse_societe` varchar(255) NOT NULL,
  `numero_telephone_societe` int NOT NULL,
  `email_societe` varchar(255) NOT NULL,
  `description_societe` varchar(255) NOT NULL,
  PRIMARY KEY (`id_societe`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `societe`
--

INSERT INTO `societe` (`id_societe`, `nom_societe`, `secteur_activite_societe`, `adresse_societe`, `numero_telephone_societe`, `email_societe`, `description_societe`) VALUES
(1, 'TechCorp', 'Informatique', '12 Rue des Startups', 123456789, 'contact@techcorp.com', 'Entreprise de technologie innovante'),
(2, 'MediPlus', 'Santé', '34 Avenue des Hôpitaux', 987654321, 'info@mediplus.com', 'Société spécialisée dans les équipements médicaux');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `Annonce_Administrateurs_FK` FOREIGN KEY (`id_admin`) REFERENCES `administrateurs` (`id_admin`),
  ADD CONSTRAINT `Annonce_Categorie1_FK` FOREIGN KEY (`id_categorie`) REFERENCES `categorie` (`id_categorie`),
  ADD CONSTRAINT `Annonce_Societe0_FK` FOREIGN KEY (`id_societe`) REFERENCES `societe` (`id_societe`);

--
-- Contraintes pour la table `candidature`
--
ALTER TABLE `candidature`
  ADD CONSTRAINT `Candidature_Annonce_FK` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`),
  ADD CONSTRAINT `Candidature_Demandeurs0_FK` FOREIGN KEY (`id_demandeurs`) REFERENCES `demandeurs` (`id_demandeurs`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
