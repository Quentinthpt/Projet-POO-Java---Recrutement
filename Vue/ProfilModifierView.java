package Vue;

import Modele.SessionUtilisateur;
import DAO.UtilisateurDAOImpl;

import javax.swing.*;
import java.awt.*;

public class ProfilModifierView extends JFrame {
    public ProfilModifierView() {
        setTitle("Modifier mon profil");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;

        SessionUtilisateur session = SessionUtilisateur.getInstance();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(blanc);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Modifier mes informations", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(bleuFonce);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Champs modifiables
        JTextField nomField = new JTextField(session.getNom());
        JTextField prenomField = new JTextField(session.getPrenom());
        JTextField emailField = new JTextField(session.getEmail());
        JTextField adresseField = new JTextField(session.getAdresse());
        JTextField ageField = new JTextField(String.valueOf(session.getAge()));
        JTextField experienceField = new JTextField(session.getExperience());
        JTextField cvField = new JTextField(session.getCv());

        String[] labels = {"Nom", "Prénom", "Email", "Adresse", "Âge", "Expérience", "CV"};
        JTextField[] fields = {nomField, prenomField, emailField, adresseField, ageField, experienceField, cvField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            panel.add(new JLabel(labels[i] + " :"), gbc);

            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }

        // Boutons
        JButton enregistrerBtn = new JButton("Enregistrer");
        enregistrerBtn.setBackground(bleuClair);
        enregistrerBtn.setForeground(blanc);

        JButton annulerBtn = new JButton("Annuler");
        annulerBtn.setBackground(Color.GRAY);
        annulerBtn.setForeground(blanc);

        gbc.gridy = labels.length + 2;
        gbc.gridx = 0;
        panel.add(enregistrerBtn, gbc);
        gbc.gridx = 1;
        panel.add(annulerBtn, gbc);

        // Listeners
        enregistrerBtn.addActionListener(e -> {
            try {
                session.setNom(nomField.getText());
                session.setPrenom(prenomField.getText());
                session.setEmail(emailField.getText());
                session.setAdresse(adresseField.getText());
                session.setAge(Integer.parseInt(ageField.getText()));
                session.setExperience(experienceField.getText());
                session.setCv(cvField.getText());

                //System.out.println("ID session: " + session.getId());

                UtilisateurDAOImpl dao = new UtilisateurDAOImpl();
                boolean updated = dao.mettreAJourProfil(session);

                if (updated) {
                    JOptionPane.showMessageDialog(this, "Profil mis à jour en base de données !");
                    new ProfilPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'âge doit être un nombre valide.");
            }
        });

        annulerBtn.addActionListener(e -> dispose());

        add(panel);
        setVisible(true);
    }
}
