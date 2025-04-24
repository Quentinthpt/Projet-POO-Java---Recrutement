package Vue;

import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPage extends JFrame {
    public MainPage() {
        setTitle("MatchaJob - Recrutement");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Couleurs principales
        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        // Ajout des composants
        mainPanel.add(new HeaderComponent(this), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(new FooterComponent(), BorderLayout.SOUTH);

        // Configuration du défilement
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Style de la barre de défilement
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.WHITE);
        verticalScrollBar.setForeground(new Color(45, 132, 255));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));

        add(scrollPane);
        setVisible(true);
    }

    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private JPanel createContentPanel() {
        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(bleuFonce);

        JPanel corps_de_la_page = new JPanel(new GridLayout(2, 2, 15, 10));
        corps_de_la_page.setBackground(bleuFonce);
        corps_de_la_page.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Dimensions communes pour les images
        int imageWidth = 400;
        int imageHeight = 300;

        // Case 1.1 - Image d'accroche
        ImageIcon corps_1_1 = new ImageIcon("src/images/groupe.jpg");
        corps_1_1 = resizeImage(corps_1_1, imageWidth, imageHeight);
        JLabel img_1_1 = new JLabel(corps_1_1, JLabel.CENTER);
        img_1_1.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        corps_de_la_page.add(img_1_1);

        // Case 1.2 - Message accrocheur
        JLabel corps_1_2 = new JLabel("<html><div style='width: 400px;'>"
                + "<h2 style='color: #ffffff;'>Trouver un job n'a jamais été aussi simple !</h2>"
                + "<p style='font-size: 14px;'>Chez nous, vous êtes bien plus qu'un simple CV. "
                + "Notre équipe de conseillers passionnés vous accompagne à chaque étape, du choix de l'offre à l'entretien final. "
                + "Fini les arnaques et les promesses floues : chaque annonce est vérifiée, chaque recruteur est authentifié.</p>"
                + "<p style='margin-top: 10px;'>Notre objectif ? Que vous trouviez rapidement un emploi qui vous correspond, "
                + "et que vous n'ayez plus besoin de nous 😉</p>"
                + "</div></html>", JLabel.LEFT);
        corps_1_2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        corps_1_2.setForeground(blanc);
        corps_1_2.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 10));
        corps_de_la_page.add(corps_1_2);

        // Case 2.1 - Deuxième image
        ImageIcon corps_2_1 = new ImageIcon("src/images/marche.jpeg");
        corps_2_1 = resizeImage(corps_2_1, imageWidth, imageHeight);
        JLabel img_2_1 = new JLabel(corps_2_1, JLabel.CENTER);
        img_2_1.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        corps_de_la_page.add(img_2_1);

        // Case 2.2 - Infos + conseils
        JLabel corps_2_2 = new JLabel("<html><div style='width: 400px;'>"
                + "<h2 style='color: #ffffff;'>Boostez votre avenir professionnel</h2>"
                + "<p style='font-size: 14px;'>Accédez à des contenus exclusifs : conseils pour réussir vos entretiens, "
                + "astuces pour valoriser votre profil, et décryptage des tendances du marché de l'emploi.</p>"
                + "<p style='margin-top: 10px;'>Chaque jour, de nouvelles opportunités vous attendent. "
                + "Notre plateforme intelligente vous recommande les meilleures offres, adaptées à vos compétences et à vos envies.</p>"
                + "</div></html>", JLabel.LEFT);
        corps_2_2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        corps_2_2.setForeground(blanc);
        corps_2_2.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 10));
        corps_de_la_page.add(corps_2_2);

        contentPanel.add(corps_de_la_page);
        contentPanel.add(Box.createVerticalStrut(20));



        return contentPanel;
    }
}