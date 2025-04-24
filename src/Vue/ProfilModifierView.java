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
        //JTextField cvField = new JTextField(session.getCv());


        JTextField cvField = new JTextField(session.getCv());
        cvField.setEditable(false); // on empêche la modification manuelle

        JButton choisirFichierBtn = new JButton("Choisir un fichier");
        choisirFichierBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Crée le dossier assets/cv s'il n'existe pas
                    java.nio.file.Path destDir = java.nio.file.Paths.get("assets/cv");
                    java.nio.file.Files.createDirectories(destDir);

                    // Copie le fichier dans le dossier
                    java.nio.file.Path destPath = destDir.resolve(selectedFile.getName());
                    java.nio.file.Files.copy(selectedFile.toPath(), destPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                    cvField.setText(selectedFile.getName()); // Met à jour le champ texte avec le nom du fichier
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la copie du fichier : " + ex.getMessage());
                }
            }
        });




        String[] labels = {"Nom", "Prénom", "Email", "Adresse", "Âge", "Expérience", "CV"};
        JTextField[] fields = {nomField, prenomField, emailField, adresseField, ageField, experienceField, cvField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            panel.add(new JLabel(labels[i] + " :"), gbc);

            gbc.gridx = 1;
            //panel.add(fields[i], gbc);

            if (i == 6) { // Pour le champ CV
                JPanel cvPanel = new JPanel(new BorderLayout(5, 0));
                cvPanel.add(fields[i], BorderLayout.CENTER);
                cvPanel.add(choisirFichierBtn, BorderLayout.EAST);
                panel.add(cvPanel, gbc);
            } else {
                panel.add(fields[i], gbc);
            }

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


                UtilisateurDAOImpl dao = new UtilisateurDAOImpl();
                boolean updated = dao.mettreAJourProfil(session);

                if (updated) {
                    JOptionPane.showMessageDialog(this, "Profil mis à jour en base de données !");
                    dispose();
                    new ProfilPage();
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
