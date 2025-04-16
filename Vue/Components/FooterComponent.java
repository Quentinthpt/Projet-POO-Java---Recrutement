package Vue.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class FooterComponent extends JPanel {
    public FooterComponent() {
        Color fondBas = new Color(155, 182, 243);

        setLayout(new BorderLayout());
        setBackground(fondBas);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panel principal avec BoxLayout pour disposition verticale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(fondBas);

        // Texte de contact
        JPanel textPanel = createContactTextPanel(fondBas);

        // Carte interactive
        JPanel mapPanel = createInteractiveMapPanel();

        // Ajout des composants avec "glue" pour l'espacement
        mainPanel.add(textPanel);
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(mapPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createContactTextPanel(Color bgColor) {
        String text = "<html><div style='width: 300px;'>"
                + "<b style='font-size:14px'>MatchaJob</b><br>"
                + "Un job à ton goût... vertueux comme du matcha<br><br>"
                + "<b>Contact:</b><br>"
                + "10 rue Sextius Michel, 75015 Paris<br>"
                + "Mail: contact@matchajob.com<br>"
                + "Tél: +33 (0) 6 15 08 75 05"
                + "</div></html>";

        JLabel contactLabel = new JLabel(text);
        contactLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        contactLabel.setBackground(bgColor);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(bgColor);
        textPanel.add(contactLabel, BorderLayout.NORTH);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));

        return textPanel;
    }

    private JPanel createInteractiveMapPanel() {
        ImageIcon originalIcon = new ImageIcon("images/ECE_plan.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(350, 150, Image.SCALE_SMOOTH);
        ImageIcon mapIcon = new ImageIcon(scaledImage);

        JLabel mapLabel = new JLabel(mapIcon);
        mapLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mapLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Tooltip pour indiquer que c'est cliquable
        mapLabel.setToolTipText("Cliquez pour voir sur Google Maps");

        mapLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openGoogleMaps();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mapLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mapLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });

        // Panel pour la carte avec contraintes de taille
        JPanel mapPanel = new JPanel(new BorderLayout());
        mapPanel.setBackground(new Color(155, 182, 243));
        mapPanel.setMaximumSize(new Dimension(400, 180));
        mapPanel.add(mapLabel, BorderLayout.CENTER);

        return mapPanel;
    }

    private void openGoogleMaps() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/maps/place/10+Rue+Sextius+Michel,+75015+Paris"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Impossible d'ouvrir Google Maps",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}