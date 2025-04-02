#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Demandeurs
#------------------------------------------------------------

CREATE TABLE Demandeurs(
        id_demandeurs        Int  Auto_increment  NOT NULL ,
        nom_demandeur        Varchar (255) NOT NULL ,
        prenom_demandeur     Varchar (255) NOT NULL ,
        age_demandeur        Int NOT NULL ,
        e_mail_demandeur     Varchar (255) NOT NULL ,
        adresse_demandeur    Varchar (255) NOT NULL ,
        experience_demandeur Varchar (255) NOT NULL ,
        cv_demandeur         Varchar (255) NOT NULL ,
        pw_demandeur         Varchar (255) NOT NULL
	,CONSTRAINT Demandeurs_PK PRIMARY KEY (id_demandeurs)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Société
#------------------------------------------------------------

CREATE TABLE Societe(
        id_societe               Int  Auto_increment  NOT NULL ,
        nom_societe              Varchar (255) NOT NULL ,
        secteur_activite_societe Varchar (255) NOT NULL ,
        adresse_societe          Varchar (255) NOT NULL ,
        numero_telephone_societe Int NOT NULL ,
        email_societe            Varchar (255) NOT NULL ,
        description_societe      Varchar (255) NOT NULL
	,CONSTRAINT Societe_PK PRIMARY KEY (id_societe)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Administrateurs
#------------------------------------------------------------

CREATE TABLE Administrateurs(
        id_admin     Int  Auto_increment  NOT NULL ,
        nom_admin    Varchar (255) NOT NULL ,
        prenom_admin Varchar (255) NOT NULL ,
        e_mail_admin Varchar (255) NOT NULL ,
        pw_admin     Varchar (255) NOT NULL
	,CONSTRAINT Administrateurs_PK PRIMARY KEY (id_admin)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Categorie
#------------------------------------------------------------

CREATE TABLE Categorie(
        id_categorie          Int  Auto_increment  NOT NULL ,
        nom_categorie         Varchar (255) NOT NULL ,
        dexcription_categorie Varchar (255) NOT NULL
	,CONSTRAINT Categorie_PK PRIMARY KEY (id_categorie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Annonce
#------------------------------------------------------------

CREATE TABLE Annonce(
        id_annonce                 Int  Auto_increment  NOT NULL ,
        titre_annonce              Varchar (255) NOT NULL ,
        description_annonce        Varchar (255) NOT NULL ,
        experience_requise_annonce Varchar (255) NOT NULL ,
        salaire_annonce            Int NOT NULL ,
        date_debut_annonce         Date NOT NULL ,
        statut_annonce             Varchar (255) NOT NULL ,
        lieu_travail_annonce       Varchar (255) NOT NULL ,
        type_contrat_annonce       Varchar (255) NOT NULL ,
        id_admin                   Int ,
        id_societe                 Int ,
        id_categorie               Int
	,CONSTRAINT Annonce_PK PRIMARY KEY (id_annonce)

	,CONSTRAINT Annonce_Administrateurs_FK FOREIGN KEY (id_admin) REFERENCES Administrateurs(id_admin)
	,CONSTRAINT Annonce_Societe0_FK FOREIGN KEY (id_societe) REFERENCES Societe(id_societe)
	,CONSTRAINT Annonce_Categorie1_FK FOREIGN KEY (id_categorie) REFERENCES Categorie(id_categorie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Candidature
#------------------------------------------------------------

CREATE TABLE Candidature(
        id_annonce            Int NOT NULL ,
        id_demandeurs         Int NOT NULL ,
        date_candidature      Date NOT NULL ,
        statut_candidature    Varchar (255) NOT NULL ,
        note_candidature      Int NOT NULL ,
        documents_candidature Varchar (255) NOT NULL
	,CONSTRAINT Candidature_PK PRIMARY KEY (id_annonce,id_demandeurs)

	,CONSTRAINT Candidature_Annonce_FK FOREIGN KEY (id_annonce) REFERENCES Annonce(id_annonce)
	,CONSTRAINT Candidature_Demandeurs0_FK FOREIGN KEY (id_demandeurs) REFERENCES Demandeurs(id_demandeurs)
)ENGINE=InnoDB;

