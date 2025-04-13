package Vue;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPage extends JFrame {
    public MainPage() {
        //        JFrame frame = new JFrame();
        //        frame.setTitle("Recrutement");
        //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        frame.setSize(1600, 900);
        //        frame.isBackgroundSet();
        //        frame.setLocationRelativeTo(null);
        //
        //        JPanel page = new JPanel(new BorderLayout());
        //        JPanel top = new JPanel(new FlowLayout());
        //        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\admin\\Desktop\\logo.png"));
        //        logo.setBorder(BorderFactory.createLineBorder(Color.black));
        //        top.add(logo);
        //        page.add(top, BorderLayout.NORTH);

        //  frame.setVisible(true);
        setTitle("MatchaJob - Recrutement");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Couleurs principales
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;

        // Panel principal avec fond bleu foncé
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        // Barre de navigation
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(blanc);
        topNav.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Logo image + texte
        ImageIcon logoIcon = new ImageIcon("images/telechargement2.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogo);

        JLabel logoLabel = new JLabel(" MatchaJob", logoIcon, JLabel.LEFT);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoLabel.setForeground(bleuFonce);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        menuPanel.setBackground(blanc);

        String[] menuItems = {"trouver un emploi", "candidats", "recruteurs", "nos agences", "à propos"};
        for (String item : menuItems) {
            JLabel label = new JLabel(item);
            label.setFont(new Font("SansSerif", Font.PLAIN, 16));
            label.setForeground(bleuFonce);
            menuPanel.add(label);
        }

        JPanel rightMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        rightMenu.setBackground(blanc);
        rightMenu.add(new JLabel("♡ 0"));
        rightMenu.add(new JLabel("👤 mon compte"));

        topNav.add(logoLabel, BorderLayout.WEST);
        topNav.add(menuPanel, BorderLayout.CENTER);
        topNav.add(rightMenu, BorderLayout.EAST);

        // Zone de recherche
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        searchPanel.setBackground(bleuFonce);

        JTextField jobField = new JTextField("métier, mot clé", 20);
        JTextField locationField = new JTextField("département, ville", 20);
        String[] distances = {"5 km", "10 km", "20 km", "50 km"};
        JComboBox<String> distanceBox = new JComboBox<>(distances);
        JButton searchButton = new JButton("Voir les offres");
        searchButton.setBackground(bleuClair);
        searchButton.setForeground(blanc);

        searchPanel.add(jobField);
        searchPanel.add(locationField);
        searchPanel.add(distanceBox);
        searchPanel.add(searchButton);

        // Texte offres
        JLabel offresLabel = new JLabel("486 offres d'emploi : services aux entreprises");
        offresLabel.setForeground(blanc);
        offresLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        offresLabel.setBorder(new EmptyBorder(20, 20, 10, 0));

        // Image de fond ou illustration centrale
        ImageIcon bannerIcon = new ImageIcon("images/telechargement1.png");
        Image scaledBanner = bannerIcon.getImage().getScaledInstance(1100, 300, Image.SCALE_SMOOTH);
        bannerIcon = new ImageIcon(scaledBanner);
        JLabel bannerLabel = new JLabel(bannerIcon);
        bannerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Conteneur vertical
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(bleuFonce);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(offresLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(bannerLabel);

        // Ajout au panel principal
        mainPanel.add(topNav, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);


        add(mainPanel);
        setVisible(true);
    }

}





/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainPage extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage){
        this.primaryStage = stage;

        Label titre = new Label("Bonjour, bienvenue sur notre site de Recrutement! ");
        Button bouton1 = new Button("Connexion");
        Button bouton2 = new Button("Inscription");

        bouton1.setOnAction(e -> Connexion());
        bouton2.setOnAction(e -> Inscription());

        VBox layout = new VBox(15, titre, bouton1, bouton2);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Recrutement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
/*
    public MainPage() {

        JFrame frame = new JFrame();
        frame.setTitle("Recrutement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.isBackgroundSet();
        frame.setLocationRelativeTo(null);


        //setTitle("Recrutement");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(1600, 900);
        //setLocationRelativeTo(null);




        JPanel page = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout());
        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\admin\\Desktop\\logo.png"));
        logo.setBorder(BorderFactory.createLineBorder(Color.black));
        top.add(logo);




        label1.setFont(new Font("Tahoma", Font.BOLD, 20));
        page.add(top, BorderLayout.NORTH);
        page.add(label1, SwingConstants.CENTER);

        //add(page);

        //setVisible(true);
    }
 */
/*
    private void Connexion() {
        Label label = new Label("Connexion");
        Button retour_c = new Button("Retour à la page d'accueil");
        retour_c.setOnAction(e -> start(primaryStage));

        VBox layout = new VBox(15, label, retour_c);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    private void Inscription() {
        Label label = new Label("Inscription");
        Button retour_i = new Button("Retour à la page d'accueil");
        retour_i.setOnAction(e -> start(primaryStage));

        VBox layout = new VBox(15, label, retour_i);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX depuis MainPage
    }


}
*/