package Vue.Components;

import javax.swing.*;
import java.awt.*;

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

        add(texteBasPage, BorderLayout.CENTER);
    }
}