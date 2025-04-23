package service;
import config.Config;

import java.util.Properties;

public class Services {

    public static final EmailService EMAIL;


//    // Récupère les identifiants depuis les variables d’environnement
//    private static final String USER = System.getenv("MAIL_USER"); // ex. yourmail@gmail.com
//    private static final String PASS = System.getenv("MAIL_PASS"); // mot de passe d’application
//    private static final String FROM = "recrutement.app@gmail.com";

//    public static final EmailService EMAIL = new EmailService(
//            "smtp.gmail.com", 587, USER, PASS, FROM
//    );

    static {
        // ⚠️ Replace with your real config or load from .env/properties
//        String host = "smtp.gmail.com";
//        int port = 587;
//        String username = "abdelhadi.alaouibalghiti00@gmail.com";
//        String password = "avih bitn rofs vadw";
//        String from = "abdelhadi.alaouibalghiti00@gmail.com";
//
//        EMAIL = new EmailService(host, port, username, password, from);
        Properties props = Config.loadEmailConfig();

        EMAIL = new EmailService(
                props.getProperty("smtp.host"),
                Integer.parseInt(props.getProperty("smtp.port")),
                props.getProperty("smtp.username"),
                props.getProperty("smtp.password"),
                props.getProperty("smtp.from"));
    }


    private Services() { } // utilitaire, pas d'instanciation
}
