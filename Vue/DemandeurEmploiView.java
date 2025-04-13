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
        Color blanc = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        // Header
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(blanc);
        topNav.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel logoLabel = new JLabel(" MatchaJob");
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoLabel.setForeground(bleuFonce);

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

        rightMenu.add(monCompte);
        topNav.add(logoLabel, BorderLayout.WEST);
        topNav.add(rightMenu, BorderLayout.EAST);

        JLabel welcome = new JLabel("Bienvenue " + demandeurEmploi.getPrenom() + " !");
        welcome.setForeground(blanc);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(new EmptyBorder(40, 10, 10, 10));

        mainPanel.add(topNav, BorderLayout.NORTH);
        mainPanel.add(welcome, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
