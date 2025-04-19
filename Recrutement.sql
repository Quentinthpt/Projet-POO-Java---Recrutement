-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 19 avr. 2025 à 12:02
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `annonce`
--

INSERT INTO `annonce` (`id_annonce`, `titre_annonce`, `description_annonce`, `experience_requise_annonce`, `salaire_annonce`, `date_debut_annonce`, `statut_annonce`, `lieu_travail_annonce`, `type_contrat_annonce`, `id_admin`, `id_societe`, `id_categorie`) VALUES
(1, 'Développeur Full Stack', 'Recherche développeur full stack expérimenté', '3 ans', 45000, '2025-05-01', 'Ouvert', 'Paris', 'CDI', 1, 1, 1),
(7, 'AZEAE', 'QSDQD', '2-8 ans', 1000000000, '2025-08-16', '', 'Paris', 'Stage', 1, 1, 1),
(8, 'Innsertion', 'Cette alternance est faite pour s\'insérer dans la société', '2-8 ans', 1200, '2025-04-16', '', 'Massy Palaiseau', 'Alternance', 1, 1, 1),
(9, 'Test', 'test', '0-2 ans', 400000, '2029-04-17', '', 'Paris', 'CDD', 1, 1, 1),
(10, 'CACA', 'CACA', '+8 ans', 20, '2027-04-18', '', 'CACA Land', 'Stage', 1, 1, 1);

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
(1, 4, '2025-04-16', 'En attente', 0, 'lettre_motivation_4.pdf'),
(1, 5, '2025-04-16', 'En attente', 0, 'lettre_motivation_5.pdf'),
(1, 7, '2025-04-16', 'En attente', 0, 'lettre_motivation_7.pdf'),
(1, 9, '2025-04-18', 'En attente', 0, 'lettre_motivation_9.pdf'),
(7, 1, '2025-04-17', 'En attente', 0, 'lettre_motivation_1.pdf'),
(7, 7, '2025-04-16', 'En attente', 0, 'lettre_motivation_7.pdf'),
(7, 9, '2025-04-18', 'Refusé', 100, ''),
(8, 1, '2025-04-17', 'En attente', 0, 'lettre_motivation_1.pdf'),
(8, 7, '2025-04-17', 'En attente', 0, 'lettre_motivation_7.pdf'),
(9, 7, '2025-04-17', 'En attente', 0, '');

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `demandeurs`
--

INSERT INTO `demandeurs` (`id_demandeurs`, `nom_demandeur`, `prenom_demandeur`, `age_demandeur`, `e_mail_demandeur`, `adresse_demandeur`, `experience_demandeur`, `cv_demandeur`, `pw_demandeur`) VALUES
(1, 'Dupont', 'Jean', 30, 'jean.dupont@email.com', '123 Rue de Paris', '5 ans', 'cv_jean.pdf', 'password123'),
(2, 'Martin', 'Sophie', 28, 'sophie.martin@email.com', '456 Rue de Lyon', '3 ans', 'cv_sophie.pdf', 'securepass'),
(3, 'Lemoine', 'Paul', 35, 'paul.lemoine@email.com', '789 Rue de Marseille', '10 ans', 'cv_paul.pdf', 'paulpass'),
(4, 'STITOU', 'Ranya', 21, 'ranya.stitou@email.com', '10 Rue Sextius Michel', '7 ans', 'test.pdf', 'ranya123'),
(5, '', 'Antoine', 20, 'antoine@lallement', '7 sente ', '20', 'azear', '12345'),
(7, '2', '2', 2, '2', '2', '2', '2', '2'),
(8, '2', '2', 2, '2', '2', '2', '2', '2'),
(9, '1', '1', 1, '1', '1', '1', '1', '1');

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_utilisateur` int DEFAULT NULL,
  `message` text,
  `lu` tinyint(1) DEFAULT '0',
  `date_notification` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=303 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `societe`
--

INSERT INTO `societe` (`id_societe`, `nom_societe`, `secteur_activite_societe`, `adresse_societe`, `numero_telephone_societe`, `email_societe`, `description_societe`) VALUES
(1, 'TechCorp', 'Informatique', '12 Rue des Startups', 123456789, 'contact@techcorp.com', 'Entreprise de technologie innovante'),
(2, 'MediPlus', 'Santé', '34 Avenue des Hôpitaux', 987654321, 'info@mediplus.com', 'Société spécialisée dans les équipements médicaux'),
(3, 'InnovaTech', 'Informatique', '1 Rue du Code', 101000001, 'contact@innovatech.com', 'Solutions informatiques sur mesure'),
(4, 'DevNova', 'Informatique', '2 Avenue du Numérique', 101000002, 'hello@devnova.com', 'Développement de logiciels modernes'),
(5, 'SoftEdge', 'Informatique', '3 Boulevard des Données', 101000003, 'info@softedge.com', 'Expertise en logiciels embarqués'),
(6, 'CodeFactory', 'Informatique', '4 Rue des Algorithmes', 101000004, 'support@codefactory.com', 'Usine à code pour entreprises'),
(7, 'PixelWave', 'Informatique', '5 Chemin du Graphisme', 101000005, 'contact@pixelwave.com', 'Création d’interfaces et UX'),
(8, 'NextGenIT', 'Informatique', '6 Impasse de la Tech', 101000006, 'team@nextgenit.com', 'Services IT nouvelle génération'),
(9, 'LogicIA', 'Informatique', '7 Place de l’IA', 101000007, 'ia@logicia.com', 'Solutions d’intelligence artificielle'),
(10, 'WebBridge', 'Informatique', '8 Rue des Réseaux', 101000008, 'info@webbridge.com', 'Création de plateformes web'),
(11, 'AppStorm', 'Informatique', '9 Avenue des Apps', 101000009, 'contact@appstorm.com', 'Applications mobiles innovantes'),
(12, 'DataMinds', 'Informatique', '10 Rue de la Data', 101000010, 'contact@dataminds.com', 'Analyse de données avancée'),
(13, 'CyberSafe', 'Informatique', '11 Boulevard de la Sécurité', 101000011, 'support@cybersafe.com', 'Solutions en cybersécurité'),
(14, 'CloudLink', 'Informatique', '12 Rue du Cloud', 101000012, 'cloud@cloudlink.com', 'Infrastructure cloud flexible'),
(15, 'TechNova', 'Informatique', '13 Allée du Futur', 101000013, 'info@technova.com', 'Innovation technologique continue'),
(16, 'NetPulse', 'Informatique', '14 Voie des Protocoles', 101000014, 'support@netpulse.com', 'Services réseaux et serveurs'),
(17, 'ScriptHub', 'Informatique', '15 Avenue du Script', 101000015, 'hello@scripthub.com', 'Développement web rapide'),
(18, 'AIWorks', 'Informatique', '16 Rue des Robots', 101000016, 'contact@aiworks.com', 'IA appliquée aux entreprises'),
(19, 'VirtuLab', 'Informatique', '17 Chemin Virtuel', 101000017, 'info@virtulab.com', 'Expériences virtuelles immersives'),
(20, 'BitFusion', 'Informatique', '18 Route des Bits', 101000018, 'fusion@bitfusion.com', 'Technologies intégrées'),
(21, 'ZetaSoft', 'Informatique', '19 Passage du Dev', 101000019, 'contact@zetasoft.com', 'Solutions logicielles'),
(22, 'NeoLogic', 'Informatique', '20 Rue de l’Innovation', 101000020, 'info@neologic.com', 'Logique d’avenir informatique'),
(23, 'GreenCode', 'Informatique', '21 Avenue Verte', 101000021, 'eco@greencode.com', 'Code éco-responsable'),
(24, 'FastDev', 'Informatique', '22 Boulevard Agile', 101000022, 'team@fastdev.com', 'Développement rapide et agile'),
(25, 'QuantumSoft', 'Informatique', '23 Rue Quantique', 101000023, 'hello@quantumsoft.com', 'Vers l’informatique quantique'),
(26, 'DevCrafters', 'Informatique', '24 Rue des Créateurs', 101000024, 'support@devcrafters.com', 'Artisanat du code'),
(27, 'SysArch', 'Informatique', '25 Impasse des Systèmes', 101000025, 'info@sysarch.com', 'Architecture système sur mesure'),
(28, 'ByteForge', 'Informatique', '26 Chemin du Binaire', 101000026, 'forge@byteforge.com', 'Forge de solutions logicielles'),
(29, 'NexaTech', 'Informatique', '27 Allée Technologique', 101000027, 'info@nexatech.com', 'Technologie de demain'),
(30, 'InfoSphere', 'Informatique', '28 Rue de l’Info', 101000028, 'sphere@infosphere.com', 'Sphère de l’information digitale'),
(31, 'CyberNet', 'Informatique', '29 Route du Net', 101000029, 'net@cybernet.com', 'Solutions réseau avancées'),
(32, 'AppFusion', 'Informatique', '30 Rue des Applications', 101000030, 'hello@appfusion.com', 'Fusion d’applis web et mobile'),
(33, 'CodeNest', 'Informatique', '31 Rue du Développement', 101000031, 'team@codenest.com', 'Nid des développeurs'),
(34, 'HexaSoft', 'Informatique', '32 Impasse Hexa', 101000032, 'info@hexasoft.com', 'Solutions logicielles modulaires'),
(35, 'AlgoRise', 'Informatique', '33 Allée des Algorithmes', 101000033, 'contact@algorise.com', 'Optimisation par algorithmes'),
(36, 'SkyTech', 'Informatique', '34 Avenue du Ciel', 101000034, 'support@skytech.com', 'Informatique dans les nuages'),
(37, 'AIStream', 'Informatique', '35 Rue de l’IA', 101000035, 'info@aistream.com', 'Flux intelligents'),
(38, 'DevLink', 'Informatique', '36 Rue du Dév', 101000036, 'link@devlink.com', 'Connexion développeurs'),
(39, 'NeuroTech', 'Informatique', '37 Avenue des Neurones', 101000037, 'contact@neurotech.com', 'Techno cognitive'),
(40, 'LogiNext', 'Informatique', '38 Chemin du Savoir', 101000038, 'hello@loginext.com', 'Logique et innovation'),
(41, 'DataCraft', 'Informatique', '39 Route des Données', 101000039, 'data@datacraft.com', 'Maîtrise de la donnée'),
(42, 'CloudNova', 'Informatique', '40 Rue du Nuage', 101000040, 'contact@cloudnova.com', 'Nouveautés dans le cloud'),
(43, 'BitLogic', 'Informatique', '41 Rue des Bits', 101000041, 'info@bitlogic.com', 'Logique binaire'),
(44, 'Virtualia', 'Informatique', '42 Rue Virtuelle', 101000042, 'support@virtualia.com', 'Réalité virtuelle'),
(45, 'PixelForge', 'Informatique', '43 Allée du Design', 101000043, 'contact@pixelforge.com', 'Création graphique innovante'),
(46, 'SoftPulse', 'Informatique', '44 Rue des Pulsations', 101000044, 'info@softpulse.com', 'Technologie intuitive'),
(47, 'AIQuantum', 'Informatique', '45 Boulevard IA', 101000045, 'team@aiquantum.com', 'Intersection IA & quantique'),
(48, 'NovaByte', 'Informatique', '46 Impasse Numérique', 101000046, 'contact@novabyte.com', 'Solutions informatiques agiles'),
(49, 'TechSavvy', 'Informatique', '47 Rue Tech', 101000047, 'hello@techsavvy.com', 'Tech pour tous'),
(50, 'BinaryCore', 'Informatique', '48 Voie Binaire', 101000048, 'core@binarycore.com', 'Noyau de l’innovation'),
(51, 'CodeWave', 'Informatique', '49 Avenue du Codage', 101000049, 'info@codewave.com', 'Vagues de solutions'),
(52, 'DevSphere', 'Informatique', '50 Chemin du Dév', 101000050, 'support@devsphere.com', 'Univers du développement'),
(53, 'MediNova', 'Santé', '1 Avenue de la Santé', 102000001, 'contact@medinova.com', 'Centre d’innovation médicale'),
(54, 'BioCare', 'Santé', '2 Rue des Soins', 102000002, 'info@biocare.com', 'Soins biologiques et naturels'),
(55, 'HealthLink', 'Santé', '3 Boulevard du Bien-être', 102000003, 'contact@healthlink.com', 'Réseau de soins connectés'),
(56, 'PharmaTech', 'Santé', '4 Rue des Pharmacies', 102000004, 'support@pharmatech.com', 'Technologies pharmaceutiques'),
(57, 'Vitalis', 'Santé', '5 Chemin Vital', 102000005, 'hello@vitalis.com', 'Prévention et bien-être'),
(58, 'MediExpress', 'Santé', '6 Avenue des Urgences', 102000006, 'info@mediexpress.com', 'Transport et soins d’urgence'),
(59, 'CarePlus', 'Santé', '7 Allée du Soin', 102000007, 'support@careplus.com', 'Services médicaux personnalisés'),
(60, 'ZenClinic', 'Santé', '8 Rue de la Sérénité', 102000008, 'contact@zenclinic.com', 'Clinique orientée bien-être'),
(61, 'MedSync', 'Santé', '9 Rue Connectée', 102000009, 'info@medsync.com', 'Synchronisation des soins'),
(62, 'BioLogik', 'Santé', '10 Rue des Cellules', 102000010, 'team@biologik.com', 'Biotechnologie au service de la santé'),
(63, 'TheraLife', 'Santé', '11 Rue de la Thérapie', 102000011, 'contact@theralife.com', 'Thérapies de vie moderne'),
(64, 'PulseCare', 'Santé', '12 Rue du Pouls', 102000012, 'hello@pulsecare.com', 'Soins cardiologiques'),
(65, 'HealPro', 'Santé', '13 Boulevard de la Guérison', 102000013, 'support@healpro.com', 'Professionnels de la santé'),
(66, 'NaturaMed', 'Santé', '14 Chemin Naturel', 102000014, 'info@naturamed.com', 'Santé et médecine naturelle'),
(67, 'MedVision', 'Santé', '15 Allée Visuelle', 102000015, 'contact@medvision.com', 'Soins ophtalmologiques avancés'),
(68, 'NeoCare', 'Santé', '16 Impasse Néonatale', 102000016, 'hello@neocare.com', 'Soins pour nouveau-nés'),
(69, 'LifeClinic', 'Santé', '17 Avenue de la Vie', 102000017, 'team@lifeclinic.com', 'Clinique généraliste'),
(70, 'Dentex', 'Santé', '18 Rue du Sourire', 102000018, 'info@dentex.com', 'Soins dentaires spécialisés'),
(71, 'AquaTherm', 'Santé', '19 Route Thermale', 102000019, 'contact@aquatherm.com', 'Thérapies thermales et spa'),
(72, 'BioSens', 'Santé', '20 Rue des Sens', 102000020, 'support@biosens.com', 'Capteurs biomédicaux'),
(73, 'MediZen', 'Santé', '21 Rue Zen', 102000021, 'info@medizen.com', 'Fusion santé et relaxation'),
(74, 'CuraNova', 'Santé', '22 Chemin des Remèdes', 102000022, 'hello@curanova.com', 'Recherche en soins innovants'),
(75, 'WellMed', 'Santé', '23 Boulevard du Mieux-être', 102000023, 'contact@wellmed.com', 'Santé holistique et bien-être'),
(76, 'SanaBio', 'Santé', '24 Rue Bio', 102000024, 'support@sanabio.com', 'Médecine biologique intégrée'),
(77, 'OptiCare', 'Santé', '25 Avenue Optique', 102000025, 'info@opticare.com', 'Services de santé visuelle'),
(78, 'TheraLink', 'Santé', '26 Rue des Thérapeutes', 102000026, 'team@theralink.com', 'Réseau de thérapeutes'),
(79, 'MedActif', 'Santé', '27 Rue du Mouvement', 102000027, 'contact@medactif.com', 'Santé par le sport'),
(80, 'PharmaSphere', 'Santé', '28 Allée des Médicaments', 102000028, 'info@pharmasphere.com', 'Distribution pharmaceutique'),
(81, 'Clinika', 'Santé', '29 Impasse Médicale', 102000029, 'hello@clinika.com', 'Clinique multi-spécialités'),
(82, 'BioPulse', 'Santé', '30 Rue Bioélectrique', 102000030, 'support@biopulse.com', 'Technologie médicale avancée'),
(83, 'RehabLife', 'Santé', '31 Chemin de Réadaptation', 102000031, 'contact@rehablife.com', 'Centres de rééducation'),
(84, 'MediTouch', 'Santé', '32 Rue Tactile', 102000032, 'info@meditouch.com', 'Appareils médicaux interactifs'),
(85, 'CardioPlus', 'Santé', '33 Avenue du Cœur', 102000033, 'support@cardioplus.com', 'Cardiologie de pointe'),
(86, 'TheraPlus', 'Santé', '34 Rue Thérapeutique', 102000034, 'info@theraplus.com', 'Thérapies multiples'),
(87, 'NutriMed', 'Santé', '35 Boulevard Nutrition', 102000035, 'contact@nutrimed.com', 'Santé par la nutrition'),
(88, 'OxyClinic', 'Santé', '36 Allée de l’Oxygène', 102000036, 'team@oxyclinic.com', 'Clinique de soins respiratoires'),
(89, 'NeoTherm', 'Santé', '37 Impasse Thermale', 102000037, 'hello@neotherm.com', 'Cures et bien-être'),
(90, 'Medikare', 'Santé', '38 Rue du Patient', 102000038, 'info@medikare.com', 'Soins à domicile'),
(91, 'ZenithHealth', 'Santé', '39 Rue Zenith', 102000039, 'support@zenithhealth.com', 'Santé haut de gamme'),
(92, 'ActivaMed', 'Santé', '40 Voie Active', 102000040, 'contact@activamed.com', 'Santé en mouvement'),
(93, 'BioClinic', 'Santé', '41 Rue Biologique', 102000041, 'info@bioclinic.com', 'Clinique écoresponsable'),
(94, 'CareVision', 'Santé', '42 Rue Visuelle', 102000042, 'hello@carevision.com', 'Vision et bien-être'),
(95, 'PulseMed', 'Santé', '43 Boulevard Vital', 102000043, 'support@pulsemed.com', 'Santé vitale et monitoring'),
(96, 'Therapoint', 'Santé', '44 Rue Pointée', 102000044, 'info@therapoint.com', 'Points d’accès thérapeutiques'),
(97, 'MediLine', 'Santé', '45 Rue Connectée', 102000045, 'team@mediline.com', 'Ligne de santé connectée'),
(98, 'BioSan', 'Santé', '46 Rue Sanitaire', 102000046, 'contact@biosan.com', 'Hygiène et prévention'),
(99, 'LifePulse', 'Santé', '47 Avenue de la Vie', 102000047, 'info@lifepulse.com', 'Soins continus'),
(100, 'CareNova', 'Santé', '48 Rue des Infirmiers', 102000048, 'support@carenova.com', 'Équipe de soins à domicile'),
(101, 'HealNext', 'Santé', '49 Boulevard Santé', 102000049, 'hello@healnext.com', 'Prochaine génération de soins'),
(102, 'MediScope', 'Santé', '50 Allée Médicale', 102000050, 'contact@mediscope.com', 'Diagnostic médical avancé'),
(103, 'Capital Finance', 'Finance', '1 Place de la Bourse', 112233445, 'contact@capitalfinance.com', 'Services bancaires et financiers premium'),
(104, 'Global Invest', 'Finance', '5 Rue des Investisseurs', 223344556, 'info@globalinvest.com', 'Conseil en investissements internationaux'),
(105, 'Wealth Partners', 'Finance', '18 Avenue des Millionnaires', 334455667, 'contact@wealthpartners.com', 'Gestion de patrimoine pour clients fortunés'),
(106, 'Euro Finance Group', 'Finance', '22 Boulevard Européen', 445566778, 'info@eurofinance.com', 'Services financiers paneuropéens'),
(107, 'Prime Capital', 'Finance', '9 Rue des Banques', 556677889, 'contact@primecapital.com', 'Banque d\'affaires et financement'),
(108, 'Trust Financial', 'Finance', '37 Quai des Financiers', 667788990, 'info@trustfinancial.com', 'Solutions de confiance pour vos finances'),
(109, 'Golden Assets', 'Finance', '14 Rue de l\'Or', 778899001, 'contact@goldenassets.com', 'Gestion d\'actifs et placements'),
(110, 'First Finance', 'Finance', '3 Avenue des Premiers', 889900112, 'info@firstfinance.com', 'Premier réseau de conseillers financiers'),
(111, 'Capital & Co', 'Finance', '45 Boulevard des Capitaux', 990011223, 'contact@capitalco.com', 'Expertise en marchés financiers'),
(112, 'Monetary Solutions', 'Finance', '27 Rue Monétaire', 101112233, 'info@monetarysolutions.com', 'Solutions monétaires innovantes'),
(113, 'Finance Plus', 'Finance', '8 Impasse des Dividendes', 121314151, 'contact@financeplus.com', 'Vos finances, notre priorité'),
(114, 'Blue Chip Finance', 'Finance', '19 Avenue des Valeurs', 131415161, 'info@bluechipfinance.com', 'Investissement en valeurs sûres'),
(115, 'Horizon Financial', 'Finance', '52 Rue de l\'Horizon', 141516171, 'contact@horizonfinancial.com', 'Votre horizon financier'),
(116, 'Elite Capital', 'Finance', '6 Square des Elites', 151617181, 'info@elitecapital.com', 'Services financiers haut de gamme'),
(117, 'Prestige Finance', 'Finance', '33 Allée du Prestige', 161718192, 'contact@prestigefinance.com', 'Finance prestigieuse pour clients exigeants'),
(118, 'Optimum Wealth', 'Finance', '21 Rue de l\'Optimisation', 171819203, 'info@optimumwealth.com', 'Optimisation de votre richesse'),
(119, 'Strategic Finance', 'Finance', '47 Avenue Stratégique', 181920213, 'contact@strategicfinance.com', 'Stratégies financières sur mesure'),
(120, 'Liberty Financial', 'Finance', '12 Boulevard de la Liberté', 192021324, 'info@libertyfinancial.com', 'Votre liberté financière'),
(121, 'Excel Finance', 'Finance', '25 Rue de l\'Excellence', 203142536, 'contact@excelfinance.com', 'Excellence en services financiers'),
(122, 'United Capital', 'Finance', '38 Avenue Unie', 213243546, 'info@unitedcapital.com', 'Capital uni pour vos projets'),
(123, 'Peak Finance', 'Finance', '15 Montée des Sommets', 223344556, 'contact@peakfinance.com', 'Atteignez des sommets financiers'),
(124, 'Vantage Financial', 'Finance', '29 Rue de l\'Avantage', 233445566, 'info@vantagefinancial.com', 'Prenez l\'avantage financier'),
(125, 'Summit Capital', 'Finance', '11 Chemin des Cimes', 243546576, 'contact@summitcapital.com', 'Capital au sommet'),
(126, 'Pinnacle Finance', 'Finance', '44 Rue du Pinacle', 253647586, 'info@pinnaclefinance.com', 'Finance de haut niveau'),
(127, 'Crest Financial', 'Finance', '7 Avenue des Crêtes', 263748596, 'contact@crestfinancial.com', 'Sur la crête de la vague financière'),
(128, 'Apex Finance', 'Finance', '30 Boulevard Apex', 273849506, 'info@apexfinance.com', 'Le sommet de la finance'),
(129, 'Vertex Capital', 'Finance', '16 Rue Vertex', 283950617, 'contact@vertexcapital.com', 'Point culminant de vos investissements'),
(130, 'Zenith Financial', 'Finance', '23 Allée du Zénith', 293041526, 'info@zenithfinancial.com', 'Services financiers à leur zénith'),
(131, 'Platinum Finance', 'Finance', '2 Rue Platine', 303152637, 'contact@platinumfinance.com', 'Services financiers en platine'),
(132, 'Diamond Capital', 'Finance', '40 Avenue Diamant', 313263748, 'info@diamondcapital.com', 'Solidité du diamant pour vos finances'),
(133, 'Precious Finance', 'Finance', '13 Rue Précieuse', 323334455, 'contact@preciousfinance.com', 'Vos finances sont précieuses'),
(134, 'Bullion Financial', 'Finance', '26 Quai des Lingots', 333445566, 'info@bullionfinancial.com', 'Solidité des métaux précieux'),
(135, 'Sterling Capital', 'Finance', '5 Rue Sterling', 343556677, 'contact@sterlingcapital.com', 'Qualité sterling pour vos investissements'),
(136, 'Noble Finance', 'Finance', '48 Boulevard Noble', 353667788, 'info@noblefinance.com', 'Noblesse financière'),
(137, 'Regal Financial', 'Finance', '17 Rue Royale', 363778899, 'contact@regalfinancial.com', 'Services financiers royaux'),
(138, 'Imperial Capital', 'Finance', '31 Avenue Impériale', 373889900, 'info@imperialcapital.com', 'Approche impériale de la finance'),
(139, 'Monarch Finance', 'Finance', '10 Rue des Monarques', 383990011, 'contact@monarchfinance.com', 'Régnez sur vos finances'),
(140, 'Crown Financial', 'Finance', '24 Boulevard de la Couronne', 394001122, 'info@crownfinancial.com', 'Couronnez vos projets financiers'),
(141, 'Sovereign Capital', 'Finance', '9 Rue Souveraine', 404112233, 'contact@sovereigncapital.com', 'Capital souverain'),
(142, 'Dynasty Finance', 'Finance', '35 Avenue des Dynasties', 414223344, 'info@dynastyfinance.com', 'Finance pour dynasties'),
(143, 'Legacy Financial', 'Finance', '20 Rue du Legs', 424334455, 'contact@legacyfinancial.com', 'Créez votre legacy financier'),
(144, 'Heritage Capital', 'Finance', '43 Boulevard de l\'Héritage', 434445566, 'info@heritagecapital.com', 'Capital hérité pour l\'avenir'),
(145, 'Tradition Finance', 'Finance', '14 Rue Traditionnelle', 444556677, 'contact@traditionfinance.com', 'Finance traditionnelle de qualité'),
(146, 'Modern Financial', 'Finance', '28 Avenue Moderne', 454667788, 'info@modernfinancial.com', 'Approche moderne de la finance'),
(147, 'Innovate Capital', 'Finance', '7 Rue de l\'Innovation', 464778899, 'contact@innovatecapital.com', 'Capital innovant'),
(148, 'Future Finance', 'Finance', '19 Boulevard du Futur', 474889900, 'info@futurefinance.com', 'Finance tournée vers l\'avenir'),
(149, 'NextGen Financial', 'Finance', '32 Rue de la Nouvelle Génération', 484990011, 'contact@nextgenfinancial.com', 'Finance pour la nouvelle génération'),
(150, 'Visionary Capital', 'Finance', '6 Avenue Visionnaire', 495001122, 'info@visionarycapital.com', 'Capital visionnaire'),
(151, 'Progressive Finance', 'Finance', '21 Rue Progressive', 505112233, 'contact@progressivefinance.com', 'Approche progressive de la finance'),
(152, 'Dynamic Financial', 'Finance', '42 Boulevard Dynamique', 515223344, 'info@dynamicfinancial.com', 'Finance dynamique pour résultats dynamiques'),
(153, 'Industrie MétalPro', 'Industrie', '14 Rue des Forges', 601122334, 'contact@metalpro.com', 'Fabrication de pièces métalliques industrielles'),
(154, 'Usine Nouvelle', 'Industrie', '25 Avenue des Chantiers', 612233445, 'info@usinenouvelle.com', 'Production industrielle innovante'),
(155, 'MécaIndustrie', 'Industrie', '8 Boulevard des Machines', 623344556, 'contact@mecaindustrie.com', 'Mécanique industrielle de précision'),
(156, 'ForgeMaster', 'Industrie', '3 Impasse des Marteaux', 634455667, 'info@forgemaster.com', 'Forge et estampage de métaux'),
(157, 'Industrie Lourde France', 'Industrie', '17 Rue des Fonderies', 645566778, 'contact@industrielourde.fr', 'Équipements industriels lourds'),
(158, 'TechManufact', 'Industrie', '32 Quai de la Production', 656677889, 'info@techmanufact.com', 'Technologies de fabrication avancées'),
(159, 'Ateliers Industriels Réunis', 'Industrie', '9 Avenue des Ateliers', 667788990, 'contact@ateliersreunis.com', 'Réseau d\'ateliers industriels'),
(160, 'MétalForm', 'Industrie', '21 Rue des Presses', 678899001, 'info@metalform.com', 'Formage et découpe de métaux'),
(161, 'Industrie 4.0 Solutions', 'Industrie', '45 Boulevard Automatisé', 689900112, 'contact@industrie40.com', 'Solutions pour l\'industrie du futur'),
(162, 'Chantiers Modernes', 'Industrie', '12 Rue des Grues', 700011223, 'info@chantiersmodernes.com', 'Construction industrielle et génie civil'),
(163, 'Usinage Pro', 'Industrie', '28 Avenue des Outils', 711122334, 'contact@usinagepro.com', 'Usinage CNC de haute précision'),
(164, 'Fabrication France', 'Industrie', '5 Rue du MadeInFrance', 722233445, 'info@fabricationfrance.com', 'Production industrielle française'),
(165, 'Industrie Plastique', 'Industrie', '19 Boulevard des Polymères', 733344556, 'contact@industrieplastique.com', 'Transformation des matières plastiques'),
(166, 'Aciers & Machines', 'Industrie', '7 Rue des Aciéries', 744455667, 'info@aciersmachines.com', 'Travail de l\'acier et machines-outils'),
(167, 'RobotIndustrie', 'Industrie', '33 Allée des Robots', 755566778, 'contact@robotindustrie.com', 'Automatisation industrielle robotisée'),
(168, 'Chaudronnerie Pro', 'Industrie', '22 Rue des Soudeurs', 766677889, 'info@chaudronneriepro.com', 'Chaudronnerie industrielle sur mesure'),
(169, 'Mécanique Générale', 'Industrie', '11 Avenue des Engrenages', 777788990, 'contact@mecaniquegenerale.com', 'Mécanique industrielle générale'),
(170, 'Outillage Industriel', 'Industrie', '46 Rue des Outils', 788899001, 'info@outillage-industriel.com', 'Fabrication d\'outils industriels'),
(171, 'Industrie Textile Moderne', 'Industrie', '13 Boulevard des Tissus', 799900112, 'contact@textilemoderne.com', 'Production textile industrielle'),
(172, 'Fonderie Nationale', 'Industrie', '27 Rue des Moules', 800011223, 'info@fonderienationale.com', 'Fonderie industrielle de métaux'),
(173, 'Machines Lourdes Co', 'Industrie', '4 Impasse des Vérins', 811122334, 'contact@machineslourdes.com', 'Fabrication de machines industrielles'),
(174, 'Industrie Chimique', 'Industrie', '18 Avenue des Réacteurs', 822233445, 'info@industriechimique.com', 'Produits chimiques industriels'),
(175, 'Équipements Pro', 'Industrie', '30 Rue des Équipementiers', 833344556, 'contact@equipementspro.com', 'Équipements industriels professionnels'),
(176, 'Usine Connectée', 'Industrie', '6 Boulevard Digital', 844455667, 'info@usineconnectee.com', 'Usine intelligente 4.0'),
(177, 'MétalTech', 'Industrie', '23 Rue des Alliages', 855566778, 'contact@metaltech.com', 'Technologies métallurgiques avancées'),
(178, 'Production Plus', 'Industrie', '10 Avenue Productive', 866677889, 'info@productionplus.com', 'Optimisation des processus industriels'),
(179, 'Ateliers Mécaniques', 'Industrie', '35 Rue des Tournevis', 877788990, 'contact@ateliersmecaniques.com', 'Ateliers de mécanique industrielle'),
(180, 'Industrie Bois', 'Industrie', '16 Boulevard des Scieries', 888899001, 'info@industrie-bois.com', 'Transformation industrielle du bois'),
(181, 'AluManufact', 'Industrie', '29 Rue de l\'Aluminium', 899900112, 'contact@alumanufact.com', 'Travail industriel de l\'aluminium'),
(182, 'Conception Industrielle', 'Industrie', '2 Avenue des Plans', 900011223, 'info@conceptionindustrielle.com', 'Bureau d\'études industrielles'),
(183, 'MécaPrécision', 'Industrie', '24 Rue des Microns', 911122334, 'contact@mecaprecision.com', 'Mécanique de précision industrielle'),
(184, 'Usine Verte', 'Industrie', '37 Boulevard Écologique', 922233445, 'info@usineverte.com', 'Production industrielle durable'),
(185, 'Industrie Céramique', 'Industrie', '9 Rue des Fourneaux', 933344556, 'contact@industrieceramique.com', 'Fabrication industrielle de céramique'),
(186, 'MétalStructure', 'Industrie', '41 Avenue des Charpentes', 944455667, 'info@metalstructure.com', 'Structures métalliques industrielles'),
(187, 'Production Automatisée', 'Industrie', '15 Rue des Robots', 955566778, 'contact@productionauto.com', 'Lignes de production automatisées'),
(188, 'Chantiers Navals Ind.', 'Industrie', '26 Quai des Chantiers', 966677889, 'info@chantiersnavals.com', 'Construction navale industrielle'),
(189, 'Industrie Verrière', 'Industrie', '7 Boulevard Vitré', 977788990, 'contact@industrieverriere.com', 'Fabrication industrielle de verre'),
(190, 'MécaIndustrie 2.0', 'Industrie', '19 Rue des Automates', 988899001, 'info@mecaindustrie2.com', 'Nouvelle génération de mécanique'),
(191, 'ForgeModerne', 'Industrie', '34 Avenue des Forges', 999900112, 'contact@forgemoderne.com', 'Forge industrielle moderne'),
(192, 'Usine Digitale', 'Industrie', '11 Rue Numérique', 1010101010, 'info@usinedigitale.com', 'Transformation digitale des usines'),
(193, 'Industrie Composite', 'Industrie', '28 Boulevard des Matériaux', 1011213141, 'contact@industriecomposite.com', 'Travail des matériaux composites'),
(194, 'Production France', 'Industrie', '5 Avenue MadeInFrance', 1012324252, 'info@productionfrance.com', 'Site de production industrielle française'),
(195, 'MécaSolutions', 'Industrie', '20 Rue des Solutions', 1013435363, 'contact@mecasolutions.com', 'Solutions mécaniques industrielles'),
(196, 'Ateliers Connectés', 'Industrie', '13 Boulevard IoT', 1014546474, 'info@ateliersconnectes.com', 'Ateliers industriels connectés'),
(197, 'Industrie Aéronautique', 'Industrie', '42 Rue des Avions', 1015657585, 'contact@industriaero.com', 'Sous-traitance aéronautique'),
(198, 'MétalInnov', 'Industrie', '8 Avenue Innovante', 1016768696, 'info@metalinnov.com', 'Innovation dans le travail des métaux'),
(199, 'Grande Usine', 'Industrie', '31 Rue des Grands Halls', 1017879707, 'contact@grandeusine.com', 'Complexe industriel majeur'),
(200, 'Production Pro', 'Industrie', '16 Boulevard Industriel', 1018980818, 'info@productionpro.com', 'Professionnels de la production'),
(201, 'MécaFrance', 'Industrie', '23 Avenue Mécanique', 1019091929, 'contact@mecafrance.com', 'Mécanique industrielle française'),
(202, 'Industrie Future', 'Industrie', '10 Rue de Demain', 1020203040, 'info@industriefuture.com', 'L\'industrie de demain, aujourd\'hui'),
(203, 'Commerce & Co', 'Commerce', '1 Rue des Commerçants', 1020304050, 'contact@commerce-co.com', 'Réseau de distribution multi-produits'),
(204, 'MarketPlace Pro', 'Commerce', '15 Avenue des Boutiques', 1021314151, 'info@marketplacepro.com', 'Plateforme commerciale professionnelle'),
(205, 'Grande Distribution France', 'Commerce', '30 Boulevard des Magasins', 1022324252, 'contact@grandedistribution.fr', 'Chaîne de grande distribution'),
(206, 'E-Commerce Plus', 'Commerce', '8 Rue du Digital', 1023334353, 'info@ecommerceplus.com', 'Solutions de commerce en ligne'),
(207, 'Boutique Chic', 'Commerce', '22 Avenue de la Mode', 1024344454, 'contact@boutiquechic.com', 'Prêt-à-porter haut de gamme'),
(208, 'Discount Market', 'Commerce', '5 Rue des Promotions', 1025354555, 'info@discountmarket.com', 'Supermarché discount'),
(209, 'Epicerie Fine', 'Commerce', '17 Place du Gourmet', 1026364656, 'contact@epiceriefine.com', 'Produits alimentaires de qualité'),
(210, 'TechStore', 'Commerce', '12 Boulevard Électronique', 1027374757, 'info@techstore.com', 'Magasin d\'électronique grand public'),
(211, 'Maison & Déco', 'Commerce', '25 Rue de l\'Intérieur', 1028384858, 'contact@maisondeco.com', 'Décoration d\'intérieur'),
(212, 'Sport 2000', 'Commerce', '9 Avenue des Athlètes', 1029394959, 'info@sport2000.com', 'Équipements sportifs'),
(213, 'Jouets & Cie', 'Commerce', '3 Rue des Enfants', 1030405060, 'contact@jouets-cie.com', 'Jouets et jeux éducatifs'),
(214, 'Librairie Culturelle', 'Commerce', '18 Boulevard Littéraire', 1031415161, 'info@librairieculturelle.com', 'Librairie généraliste'),
(215, 'Beauté Shop', 'Commerce', '7 Rue des Cosmétiques', 1032425262, 'contact@beauteshop.com', 'Produits de beauté et cosmétiques'),
(216, 'Optique Vision', 'Commerce', '29 Avenue des Lunettes', 1033435363, 'info@optiquevision.com', 'Magasin d\'optique'),
(217, 'Parfumerie Luxe', 'Commerce', '14 Rue des Arômes', 1034445464, 'contact@parfumerieluxe.com', 'Parfums haut de gamme'),
(218, 'Brico Pro', 'Commerce', '11 Boulevard des Outils', 1035455565, 'info@bricopro.com', 'Magasin de bricolage'),
(219, 'Jardin Vert', 'Commerce', '6 Rue des Plantes', 1036465666, 'contact@jardinvert.com', 'Produits de jardinage'),
(220, 'Auto Accessoires', 'Commerce', '23 Avenue Automobile', 1037475767, 'info@autoaccessoires.com', 'Accessoires pour véhicules'),
(221, 'Vins & Spiritueux', 'Commerce', '10 Rue des Cépages', 1038485868, 'contact@vins-spiritueux.com', 'Caviste professionnel'),
(222, 'Fromagerie Tradition', 'Commerce', '4 Place des Fromages', 1039495969, 'info@fromagerietradition.com', 'Fromages artisanaux'),
(223, 'Boulangerie Maison', 'Commerce', '19 Rue du Pain', 1040506070, 'contact@boulangeriemaison.com', 'Boulangerie artisanale'),
(224, 'Chocolaterie Douceur', 'Commerce', '2 Boulevard Cacaoté', 1041516171, 'info@chocolateriedouceur.com', 'Chocolats artisanaux'),
(225, 'Primeurs Fraîcheur', 'Commerce', '27 Avenue des Légumes', 1042526272, 'contact@primeursfrais.com', 'Fruits et légumes frais'),
(226, 'Boucherie Charlemagne', 'Commerce', '13 Rue des Viandes', 1043536373, 'info@boucherie-charlemagne.com', 'Boucherie de qualité'),
(227, 'Poissonnerie Marine', 'Commerce', '31 Quai des Pêcheurs', 1044546474, 'contact@poissonneriemarine.com', 'Poissons et fruits de mer frais'),
(228, 'Traiteur Delice', 'Commerce', '16 Rue des Saveurs', 1045556575, 'info@traiteurdelice.com', 'Traiteur événementiel'),
(229, 'Café des Arts', 'Commerce', '5 Place des Artistes', 1046566676, 'contact@cafedesarts.com', 'Café culturel'),
(230, 'Restaurant Gourmand', 'Commerce', '20 Avenue des Gastronomes', 1047576777, 'info@restaurantgourmand.com', 'Restaurant haut de gamme'),
(231, 'Fast Food Express', 'Commerce', '9 Rue Rapide', 1048586878, 'contact@fastfoodexpress.com', 'Restauration rapide'),
(232, 'Bio Market', 'Commerce', '24 Boulevard Biologique', 1049596979, 'info@biomarket.com', 'Produits biologiques'),
(233, 'Zéro Déchet Shop', 'Commerce', '1 Rue Écologique', 1050607080, 'contact@zerodechetshop.com', 'Commerce responsable'),
(234, 'Mode Access', 'Commerce', '28 Avenue de la Création', 1051617181, 'info@modeaccess.com', 'Accessoires de mode'),
(235, 'Chaussure Plus', 'Commerce', '7 Rue des Souliers', 1052627282, 'contact@chaussureplus.com', 'Magasin de chaussures'),
(236, 'Maroquinerie Fine', 'Commerce', '15 Boulevard du Cuir', 1053637383, 'info@maroquineriefine.com', 'Articles en cuir de qualité'),
(237, 'Bijouterie Éclat', 'Commerce', '12 Place des Pierres', 1054647484, 'contact@bijouterieeclat.com', 'Bijoux et montres'),
(238, 'Horlogerie Précise', 'Commerce', '6 Rue du Temps', 1055657585, 'info@horlogerieprecise.com', 'Horlogerie de précision'),
(239, 'Art Gallery', 'Commerce', '21 Avenue des Arts', 1056667686, 'contact@artgallery.com', 'Galerie d\'art commerciale'),
(240, 'Musique Store', 'Commerce', '3 Boulevard des Notes', 1057677787, 'info@musicstore.com', 'Instruments de musique'),
(241, 'Jeux Vidéo Planet', 'Commerce', '18 Rue des Gamers', 1058687888, 'contact@jeuxvideoplanet.com', 'Jeux vidéo et consoles'),
(242, 'High-Tech Zone', 'Commerce', '10 Avenue Technologique', 1059697989, 'info@hightechzone.com', 'Équipements high-tech'),
(243, 'Mobilier Design', 'Commerce', '25 Rue des Meubles', 1060708090, 'contact@mobilierdesign.com', 'Meubles contemporains'),
(244, 'Literie Confort', 'Commerce', '8 Boulevard du Sommeil', 1061718191, 'info@literieconfort.com', 'Literie de qualité'),
(245, 'Électroménager Pro', 'Commerce', '14 Rue des Appareils', 1062728292, 'contact@electromenagerpro.com', 'Électroménager professionnel'),
(246, 'Bébé Store', 'Commerce', '29 Avenue des Nourrissons', 1063738393, 'info@bebestore.com', 'Articles pour bébés'),
(247, 'Animalerie Zen', 'Commerce', '11 Rue des Animaux', 1064748494, 'contact@animaleriezen.com', 'Produits pour animaux'),
(248, 'Fleuriste Nature', 'Commerce', '4 Boulevard Florale', 1065758595, 'info@fleuristenature.com', 'Fleurs et compositions'),
(249, 'Cadeaux & Vous', 'Commerce', '17 Rue des Surprises', 1066768696, 'contact@cadeauxetvous.com', 'Idées cadeaux originales'),
(250, 'Occasion Or', 'Commerce', '22 Avenue des Bonnes Affaires', 1067778797, 'info@occasionor.com', 'Articles d\'occasion de qualité'),
(251, 'Dépôt Vente Premium', 'Commerce', '9 Rue de la Seconde Main', 1068788898, 'contact@depotventepremium.com', 'Dépôt-vente haut de gamme'),
(252, 'Commerce Solidaire', 'Commerce', '5 Place de l\'Échange', 1069798999, 'info@commercesolidaire.com', 'Commerce équitable et solidaire'),
(253, 'Service Plus', 'Service', '12 Rue des Prestations', 1070809010, 'contact@serviceplus.com', 'Services multisectoriels aux professionnels'),
(254, 'QualiService', 'Service', '25 Avenue de la Qualité', 1071819111, 'info@qualiservice.com', 'Services certifiés qualité'),
(255, 'ProxiServices', 'Service', '8 Boulevard de Proximité', 1072829212, 'contact@proxiservices.com', 'Services de proximité'),
(256, 'Service Elite', 'Service', '3 Place Premium', 1073839313, 'info@serviceelite.com', 'Services haut de gamme'),
(257, 'France Services', 'Service', '17 Rue Nationale', 1074849414, 'contact@franceservices.com', 'Réseau national de services'),
(258, 'Express Service', 'Service', '30 Avenue Rapide', 1075859515, 'info@expressservice.com', 'Services urgents 24h/24'),
(259, 'Service & Co', 'Service', '11 Rue des Assistants', 1076869616, 'contact@service-co.com', 'Services aux entreprises'),
(260, 'Allo Service', 'Service', '22 Boulevard de l\'Aide', 1077879717, 'info@alloservice.com', 'Service à la demande'),
(261, 'City Services', 'Service', '5 Impasse Urbaine', 1078889818, 'contact@cityservices.com', 'Services urbains'),
(262, 'Service Pro', 'Service', '19 Avenue Professionnelle', 1079899919, 'info@servicepro.com', 'Services pour professionnels'),
(263, 'MultiServices', 'Service', '7 Rue Polyvalente', 1080901020, 'contact@multiservices.com', 'Services diversifiés'),
(264, 'Expert Services', 'Service', '14 Boulevard des Experts', 1081911121, 'info@expertservices.com', 'Services spécialisés'),
(265, 'Service Maintenance', 'Service', '28 Rue de l\'Entretien', 1082921222, 'contact@servicemaintenance.com', 'Maintenance technique'),
(266, 'Clean Services', 'Service', '9 Avenue Propre', 1083931323, 'info@cleanservices.com', 'Services de nettoyage'),
(267, 'SecurService', 'Service', '16 Place Sécurisée', 1084941424, 'contact@securservice.com', 'Services de sécurité'),
(268, 'Logis Services', 'Service', '23 Rue du Logement', 1085951525, 'info@logisservices.com', 'Services immobiliers'),
(269, 'Info Service', 'Service', '4 Boulevard Informatique', 1086961626, 'contact@infoservice.com', 'Services informatiques'),
(270, 'Transport Service', 'Service', '31 Avenue des Transports', 1087971727, 'info@transportservice.com', 'Services logistiques'),
(271, 'Compta Services', 'Service', '10 Rue Comptable', 1088981828, 'contact@comptaservices.com', 'Services comptables'),
(272, 'Juris Service', 'Service', '21 Boulevard Juridique', 1089991929, 'info@jurisservice.com', 'Services juridiques'),
(273, 'RH Services', 'Service', '6 Rue des Ressources', 1090002030, 'contact@rhservices.com', 'Services RH'),
(274, 'Marketing Services', 'Service', '18 Avenue Commerciale', 1091012131, 'info@marketingservices.com', 'Services marketing'),
(275, 'Com Service', 'Service', '2 Boulevard Communication', 1092022232, 'contact@comservice.com', 'Services de communication'),
(276, 'Event Services', 'Service', '29 Rue des Événements', 1093032333, 'info@eventservices.com', 'Organisation d\'événements'),
(277, 'Traduction Pro', 'Service', '13 Avenue Linguistique', 1094042434, 'contact@traductionpro.com', 'Services de traduction'),
(278, 'Formation Services', 'Service', '26 Rue Pédagogique', 1095052535, 'info@formationservices.com', 'Services de formation'),
(279, 'Conseil Plus', 'Service', '1 Place des Conseils', 1096062636, 'contact@conseilplus.com', 'Services de conseil'),
(280, 'Tech Services', 'Service', '15 Boulevard Technologique', 1097072737, 'info@techservices.com', 'Services techniques'),
(281, 'Eco Services', 'Service', '24 Rue Écologique', 1098082838, 'contact@ecoservices.com', 'Services environnementaux'),
(282, 'Santé Services', 'Service', '7 Avenue Médicale', 1099092939, 'info@santeservices.com', 'Services médicaux'),
(283, 'Assistance 24h', 'Service', '20 Boulevard de l\'Assistance', 1100103040, 'contact@assistance24h.com', 'Assistance permanente'),
(284, 'Senior Services', 'Service', '9 Rue des Aînés', 1101113141, 'info@seniorservices.com', 'Services pour seniors'),
(285, 'Family Services', 'Service', '33 Avenue Familiale', 1102123242, 'contact@familyservices.com', 'Services familiaux'),
(286, 'Pet Services', 'Service', '12 Rue des Animaux', 1103133343, 'info@petservices.com', 'Services pour animaux'),
(287, 'Garden Services', 'Service', '5 Boulevard des Jardins', 1104143444, 'contact@gardenservices.com', 'Services paysagers'),
(288, 'Food Services', 'Service', '27 Rue Culinaire', 1105153545, 'info@foodservices.com', 'Services alimentaires'),
(289, 'Luxe Services', 'Service', '14 Avenue du Luxe', 1106163646, 'contact@luxeservices.com', 'Services haut de gamme'),
(290, 'VIP Services', 'Service', '8 Place Exclusive', 1107173747, 'info@vipservices.com', 'Services VIP'),
(291, 'Admin Services', 'Service', '19 Rue Administrative', 1108183848, 'contact@adminservices.com', 'Services administratifs'),
(292, 'Data Services', 'Service', '3 Boulevard des Données', 1109193949, 'info@dataservices.com', 'Services de gestion de données'),
(293, 'Web Services', 'Service', '23 Avenue Internet', 1110204050, 'contact@webservices.com', 'Services web'),
(294, 'Cloud Services', 'Service', '11 Rue Virtuelle', 1111214151, 'info@cloudservices.com', 'Services cloud'),
(295, 'Mobile Services', 'Service', '30 Boulevard Mobile', 1112224252, 'contact@mobileservices.com', 'Services mobiles'),
(296, 'Smart Services', 'Service', '16 Rue Intelligente', 1113234353, 'info@smartservices.com', 'Services connectés'),
(297, 'Innov Services', 'Service', '4 Avenue Innovante', 1114244454, 'contact@innovservices.com', 'Services innovants'),
(298, 'Digital Services', 'Service', '21 Boulevard Digital', 1115254555, 'info@digitalservices.com', 'Services numériques'),
(299, 'Future Services', 'Service', '9 Rue du Futur', 1116264656, 'contact@futureservices.com', 'Services avant-gardistes'),
(300, 'Global Services', 'Service', '28 Avenue Mondiale', 1117274757, 'info@globalservices.com', 'Services internationaux'),
(301, 'Premium Services', 'Service', '13 Place Exclusive', 1118284858, 'contact@premiumservices.com', 'Services premium'),
(302, 'Essential Services', 'Service', '6 Boulevard des Essentials', 1119294959, 'info@essentialservices.com', 'Services essentiels');

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
