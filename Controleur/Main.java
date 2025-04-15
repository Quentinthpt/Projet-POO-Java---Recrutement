package Controleur;

import Vue.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //MainPage.main(args);
        //launch(args);
        //new MainPage();
        SwingUtilities.invokeLater(MainPage::new);

    }
}
