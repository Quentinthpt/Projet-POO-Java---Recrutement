package service;

public class Services {

    // Récupère les identifiants depuis les variables d’environnement
    private static final String USER = System.getenv("MAIL_USER"); // ex. yourmail@gmail.com
    private static final String PASS = System.getenv("MAIL_PASS"); // mot de passe d’application
    private static final String FROM = "recrutement.app@gmail.com";

    public static final EmailService EMAIL = new EmailService(
            "smtp.gmail.com", 587, USER, PASS, FROM
    );

    private Services() { } // utilitaire, pas d'instanciation
}
