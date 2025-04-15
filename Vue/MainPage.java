package Vue;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
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
        Color fond_bas = new Color(155, 182, 243);
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

            // Rend le label cliquable
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Ajout de l'action pour "trouver un emploi"
            if (item.equals("trouver un emploi")) {
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        dispose(); // Ferme MainPage
                        new OffreEmploiView(); // Ouvre la vue des offres
                    }

                    // Effet hover optionnel
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        label.setForeground(bleuClair);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        label.setForeground(bleuFonce);
                    }
                });
            }

            menuPanel.add(label);
        }
        JPanel rightMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        rightMenu.setBackground(blanc);
        //rightMenu.add(new JLabel("♡ 0"));
        //rightMenu.add(new JLabel("👤 mon compte"));

        JLabel monCompteLabel = new JLabel("👤 mon compte");
        monCompteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Créer le popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuConnexion = new JMenuItem("Connexion");
        JMenuItem menuInscription = new JMenuItem("Inscription");

        popupMenu.add(menuConnexion);
        popupMenu.add(menuInscription);

// Ouvrir le menu au clic
        monCompteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                popupMenu.show(monCompteLabel, evt.getX(), evt.getY());
            }
        });

        rightMenu.add(new JLabel("♡ 0"));
        rightMenu.add(monCompteLabel);

        menuConnexion.addActionListener(e -> {
            dispose(); // ferme MainPage
            new LoginView("connexion"); // ouvre LoginView en mode Connexion
        });

        menuInscription.addActionListener(e -> {
            dispose(); // ferme MainPage
            new LoginView("inscription"); // ouvre LoginView en mode Inscription
        });


        topNav.add(logoLabel, BorderLayout.WEST);
        topNav.add(menuPanel, BorderLayout.CENTER);
        topNav.add(rightMenu, BorderLayout.EAST);

        /*// Zone de recherche
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
        searchPanel.add(searchButton);*/

        // Corps de la page
        JPanel corps_de_la_page = new JPanel(new GridLayout(2, 2, 15, 10));
        corps_de_la_page.setBackground(bleuFonce);
        corps_de_la_page.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Case 1.1 - Image d’accroche
        ImageIcon corps_1_1 = new ImageIcon("images/telechargement2.png");
        JLabel img_1_1 = new JLabel(corps_1_1, JLabel.CENTER);
        img_1_1.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        corps_de_la_page.add(img_1_1);

// Case 1.2 - Message accrocheur
        JLabel corps_1_2 = new JLabel("<html><div style='width: 400px;'>"
                + "<h2 style='color: #ffffff;'>Trouver un job n’a jamais été aussi simple !</h2>"
                + "<p style='font-size: 14px;'>Chez nous, vous êtes bien plus qu’un simple CV. "
                + "Notre équipe de conseillers passionnés vous accompagne à chaque étape, du choix de l’offre à l’entretien final. "
                + "Fini les arnaques et les promesses floues : chaque annonce est vérifiée, chaque recruteur est authentifié.</p>"
                + "<p style='margin-top: 10px;'>Notre objectif ? Que vous trouviez rapidement un emploi qui vous correspond, "
                + "et que vous n’ayez plus besoin de nous 😉</p>"
                + "</div></html>", JLabel.LEFT);
        corps_1_2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        corps_1_2.setForeground(blanc);
        corps_1_2.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 10));
        corps_de_la_page.add(corps_1_2);

// Case 2.1 - Deuxième image
        ImageIcon corps_2_1 = new ImageIcon("images/telechargement2.png");
        JLabel img_2_1 = new JLabel(corps_2_1, JLabel.CENTER);
        img_2_1.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        corps_de_la_page.add(img_2_1);

// Case 2.2 - Infos + conseils
        JLabel corps_2_2 = new JLabel("<html><div style='width: 400px;'>"
                + "<h2 style='color: #ffffff;'>Boostez votre avenir professionnel</h2>"
                + "<p style='font-size: 14px;'>Accédez à des contenus exclusifs : conseils pour réussir vos entretiens, "
                + "astuces pour valoriser votre profil, et décryptage des tendances du marché de l’emploi.</p>"
                + "<p style='margin-top: 10px;'>Chaque jour, de nouvelles opportunités vous attendent. "
                + "Notre plateforme intelligente vous recommande les meilleures offres, adaptées à vos compétences et à vos envies.</p>"
                + "</div></html>", JLabel.LEFT);
        corps_2_2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        corps_2_2.setForeground(blanc);
        corps_2_2.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 10));
        corps_de_la_page.add(corps_2_2);

        // Texte offres
        JLabel offresLabel = new JLabel("486 offres d'emploi : services aux entreprises");
        offresLabel.setForeground(blanc);
        offresLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        offresLabel.setBorder(new EmptyBorder(20, 20, 10, 0));

        // Texte bas de page
        JTextArea texte_bas_page;
        String text = "MatchaJob, Un job à ton goût… vertueux comme du matcha\n\n" +
                "Contact: \n" +
                "10 rue Sextius Michel, 75010 Paris, France\n" +
                "Mail: contact@matchajob.com\n" +
                "Téléphone: +33 (0) 6 15 08 75 05\n";
        texte_bas_page = new JTextArea(text);
        texte_bas_page.setEditable(false);
        texte_bas_page.setBackground(fond_bas);
        texte_bas_page.setLineWrap(true);
        texte_bas_page.setWrapStyleWord(true);
        texte_bas_page.setFocusable(false);
        texte_bas_page.setBorder(new EmptyBorder(10, 20, 10, 20));
        texte_bas_page.setFont(new Font("SansSerif", Font.PLAIN, 10));

        // Conteneur vertical
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(bleuFonce);
        contentPanel.add(corps_de_la_page);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(offresLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(texte_bas_page);

        // Ajout au panel principal
        mainPanel.add(topNav, BorderLayout.NORTH);
        //mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);


        // Créer le JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Supprime la bordure grise par défaut

// Style personnalisé pour la barre de défilement
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.WHITE); // Fond blanc
        verticalScrollBar.setForeground(new Color(45, 132, 255)); // Couleur bleue pour le thumb
        verticalScrollBar.setUnitIncrement(16); // Vitesse de défilement plus fluide
        verticalScrollBar.setPreferredSize(new Dimension(10, 0)); // Largeur réduite

// Style du thumb (partie mobile)
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(45, 132, 255); // Bleu clair
                this.trackColor = Color.WHITE; // Fond blanc
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton(); // Supprime les flèches
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton(); // Supprime les flèches
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        add(scrollPane); // Ajoute le JScrollPane à la JFrame

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