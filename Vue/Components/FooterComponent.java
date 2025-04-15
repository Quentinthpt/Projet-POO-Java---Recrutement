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

        String text = "MatchaJob, Un job à ton goût… vertueux comme du matcha\n\n" +
                "Contact: \n" +
                "10 rue Sextius Michel, 75010 Paris, France\n" +
                "Mail: contact@matchajob.com\n" +
                "Téléphone: +33 (0) 6 15 08 75 05\n";

        JTextArea texteBasPage = new JTextArea(text);
        texteBasPage.setEditable(false);
        texteBasPage.setBackground(fondBas);
        texteBasPage.setLineWrap(true);
        texteBasPage.setWrapStyleWord(true);
        texteBasPage.setFocusable(false);
        texteBasPage.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        texteBasPage.setFont(new Font("SansSerif", Font.PLAIN, 10));

        ImageIcon mapIcon = new ImageIcon("images/ECE_plan.png");

        Image image = mapIcon.getImage();
        Image taille_image = image.getScaledInstance(450, 130, Image.SCALE_SMOOTH);

        ImageIcon taille = new ImageIcon(taille_image);

        JLabel mapLabel = new JLabel(taille);
        mapLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mapLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        mapLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/maps/place/10+Rue+Sextius+Michel,+75015+Paris"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(fondBas);
        contentPanel.add(texteBasPage, BorderLayout.CENTER);
        contentPanel.add(mapLabel, BorderLayout.EAST);

        add(contentPanel, BorderLayout.CENTER);
    }
}