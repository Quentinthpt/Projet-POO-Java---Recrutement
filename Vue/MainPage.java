package Vue;

import javax.swing.*;
import java.awt.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainPage extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage){
        this.primaryStage = stage;

        Label titre = new Label("Bonjour, bienvenue sur notre site de Recrutement! ");
        Button bouton1 = new Button("Connexion");
        Button bouton2 = new Button("Inscription");

        bouton1.setOnAction(e -> Connexion());
        bouton2.setOnAction(e -> Inscription());

        VBox layout = new VBox(15, titre, bouton1, bouton2);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Recrutement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
/*
    public MainPage() {

        JFrame frame = new JFrame();
        frame.setTitle("Recrutement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.isBackgroundSet();
        frame.setLocationRelativeTo(null);


        //setTitle("Recrutement");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(1600, 900);
        //setLocationRelativeTo(null);




        JPanel page = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout());
        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\admin\\Desktop\\logo.png"));
        logo.setBorder(BorderFactory.createLineBorder(Color.black));
        top.add(logo);




        label1.setFont(new Font("Tahoma", Font.BOLD, 20));
        page.add(top, BorderLayout.NORTH);
        page.add(label1, SwingConstants.CENTER);

        //add(page);

        //setVisible(true);
    }
 */

    private void Connexion() {
        Label label = new Label("Connexion");
        Button retour_c = new Button("Retour à la page d'accueil");
        retour_c.setOnAction(e -> start(primaryStage));

        VBox layout = new VBox(15, label, retour_c);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    private void Inscription() {
        Label label = new Label("Inscription");
        Button retour_i = new Button("Retour à la page d'accueil");
        retour_i.setOnAction(e -> start(primaryStage));

        VBox layout = new VBox(15, label, retour_i);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX depuis MainPage
    }


}
