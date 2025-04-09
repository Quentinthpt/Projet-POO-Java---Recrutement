package Vue;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPage extends JFrame {
    public MainPage() {
        JFrame frame = new JFrame();
        frame.setTitle("Recrutement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.isBackgroundSet();
        frame.setLocationRelativeTo(null);

        JPanel page = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout());
        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\admin\\Desktop\\logo.png"));
        logo.setBorder(BorderFactory.createLineBorder(Color.black));
        top.add(logo);
        page.add(top, BorderLayout.NORTH);

        frame.setVisible(true);
    }


}
