package Modele;

public class SessionUtilisateur {
    private static SessionUtilisateur instance;

    // Propriétés de base
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String role; // "Admin", "Demandeur", "Recruteur"

    // Propriétés spécifiques au demandeur
    private int age;
    private String adresse;
    private String experience;
    private String cv;

    // Constructeur privé pour Singleton
    private SessionUtilisateur() {}

    // Méthode d'accès Singleton
    public static synchronized SessionUtilisateur getInstance() {
        if (instance == null) {
            instance = new SessionUtilisateur();
        }
        return instance;
    }

    private void initUserSession(Utilisateur user){
        SessionUtilisateur session = SessionUtilisateur.getInstance();
        session.setId(user.getId());
        session.setNom(user.getNom());
        session.setPrenom(user.getPrenom());
        session.setEmail(user.getEmail());
        session.setRole(user.getType());
    }

    // ================= GETTERS & SETTERS =================

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {return this.role;}
    public void setRole(String role) {
        this.role = role;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAdresse() {return adresse;}
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getCv() {
        return cv;
    }
    public void setCv(String cv) {
        this.cv = cv;
    }

    // ================= METHODES UTILITAIRES =================

    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(role);
    }
    public boolean isDemandeur() {
        return "Demandeur".equalsIgnoreCase(role);
    }
    public boolean isRecruteur() {
        return "Recruteur".equalsIgnoreCase(role);
    }
    public void clearSession() {
        instance = null;
    }
    // Méthode pour initialiser complètement la session
    public void initSession(Utilisateur user) {
        if (user == null) return;
        this.id = user.getId();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.email = user.getEmail();
        this.role = user.getType();

        if (user instanceof DemandeurEmploi) {
            DemandeurEmploi demandeur = (DemandeurEmploi) user;
            this.age = demandeur.getAge();
            this.adresse = demandeur.getAdresse();
            this.experience = demandeur.getExperience();
            this.cv = demandeur.getCv();
        }
    }

    @Override
    public String toString() {
        return "SessionUtilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}