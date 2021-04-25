package ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {

        Scanner r = new Scanner(System.in);

        System.out.println("Generador de inserts SQL\nIngrese el nombre de la tabla");

        String tableName = r.nextLine();

        System.out.println("Ingrese el numero de inserts");

        int n = Integer.parseInt(r.nextLine());

        ArrayList<String> insertsData = new ArrayList<String>();
        System.out.println(
                "Ingrese el record con sus celdas separadas por comas (,) y despues dé enter:\nEjemplo: 'SL21', 'Juan David', 'Gomez','manager', 'M','1-Oct-20', 30000, 'B005'");
        for (int i = 0; i < n; i++) {
            insertsData.add(r.nextLine());
        }

        r.close();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("Seleccione la carpeta de destino");

        alert.showAndWait();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        System.out.println(selectedDirectory.getAbsolutePath());

        InsertsGenerator iG = new InsertsGenerator(new File(selectedDirectory.getAbsolutePath() + "/INSERTS.sql"));

        try {
            iG.generate(insertsData, tableName);
        } catch (IOException e) {

            Alert confirmation = new Alert(AlertType.ERROR);

            confirmation.setTitle("Error");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Error con la carpeta de destino, verifique y vuelva a intentar");

            confirmation.showAndWait();

        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Operación exitosa");
        confirmation.setHeaderText(null);
        confirmation.setContentText("INSERTS.sql generado exitosamente.\n\n     Ver en carpeta de destino?");

        ButtonType buttonYes = new ButtonType("Abrir carpeta", ButtonData.YES);

        ButtonType buttonNo = new ButtonType("No", ButtonData.NO);

        confirmation.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == buttonYes) {
            try {

                String s = System.getProperty("os.name").toLowerCase();

                if (s.contains("linux")) {
                    Runtime.getRuntime().exec("nautilus " + selectedDirectory.getAbsolutePath());

                } else if (s.contains("windows")) {
                    Runtime.getRuntime().exec("explorer.exe " + selectedDirectory.getAbsolutePath());
                } else if (s.contains("mac")) {
                    Runtime.getRuntime().exec("open " + selectedDirectory.getAbsolutePath());
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }
}