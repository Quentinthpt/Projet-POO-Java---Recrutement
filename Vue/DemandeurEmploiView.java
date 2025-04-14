package Vue;

import Modele.DemandeurEmploi;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DemandeurEmploiView extends JFrame{
    private DemandeurEmploi demandeurEmploi;

    public DemandeurEmploiView(DemandeurEmploi demandeurEmploi) {
        this.demandeurEmploi = demandeurEmploi;

        setTitle("Espace Demandeur - MatchaJob");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color fond_bas = new Color(155, 182, 243);
        Color blanc = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        // Header
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(blanc);
        topNav.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Logo image + texte
        ImageIcon logoIcon = new ImageIcon("images/telechargement2.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogo);

        JLabel logoLabel = new JLabel(" MatchaJob");
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

        JPanel rightMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightMenu.setBackground(blanc);
        JLabel monCompte = new JLabel("👤 Mon compte");
        monCompte.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Bandeau déroulant
        JPopupMenu compteMenu = new JPopupMenu();
        JMenuItem profil = new JMenuItem("Mon profil");
        JMenuItem candidatures = new JMenuItem("Mes candidatures");
        JMenuItem deconnexion = new JMenuItem("Déconnexion");

        compteMenu.add(profil);
        compteMenu.add(candidatures);
        compteMenu.add(deconnexion);

        monCompte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                compteMenu.show(monCompte, e.getX(), e.getY());
            }
        });

        rightMenu.add(new JLabel("♡ 0"));
        rightMenu.add(monCompte);

        // Actions
        profil.addActionListener(ev -> {
            dispose();
            new ProfilPage(demandeurEmploi);
        });

        candidatures.addActionListener(ev -> {
            dispose();
            new CandidatureView(demandeurEmploi);
        });

        deconnexion.addActionListener(ev -> {
            dispose();
            new MainPage();
        });

        topNav.add(logoLabel, BorderLayout.WEST);
        topNav.add(menuPanel, BorderLayout.CENTER);
        topNav.add(rightMenu, BorderLayout.EAST);

        JLabel welcome = new JLabel("Bienvenue " + demandeurEmploi.getPrenom() + " !");
        welcome.setForeground(blanc);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(new EmptyBorder(40, 10, 10, 10));

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

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(bleuFonce);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(texte_bas_page);


        mainPanel.add(topNav, BorderLayout.NORTH);
        mainPanel.add(welcome, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}
