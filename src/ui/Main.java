package ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    public static void main(String[] args) throws IOException {

        launch(args);

    }

    public static void showDataTypes(int i) {
        System.out.println("Tipo de dato de la columna " + (i + 1) + ":");
        System.out.println("1. Primer nombre aleatorio de una persona");
        System.out.println("2. Primer apellido aleatorio de una persona");
        System.out.println("3. Sexo aleatorio de una persona");
        System.out.println("4. Fecha de nacimiento aleatoria de una persona");
        System.out.println("5. Nombre aleatorio de un Departamento  de una empresa");
        System.out.println("6. Número aleatorio");
        System.out.println("7. Número aleatorio positivo");
        System.out.println("8. Dirección aleatoria");
        System.out.println("9. Clave foranea");

    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        InsertsGenerator ig = new InsertsGenerator();
        Scanner r = new Scanner(System.in);
        System.out.println("Ingrese el numero de tablas a generar");
        int numTablesToGenerate = Integer.parseInt(r.nextLine());

        List<String> dateGenerated = new ArrayList<String>();

        for (int i = 0; i < numTablesToGenerate; i++) {

            System.out.println("Ingrese el nombre de la tabla " + (i + 1));

            String tableName = r.nextLine();
            System.out.println("Ingrese el numero  de registros a generar para la tabla " + tableName);

            int numToRecords = Integer.parseInt(r.nextLine());

            List<String> format = new ArrayList<String>();
            System.out.println("Ingrese el número de columnas a registrar (No tenga en cuenta la PK)");
            int numOfColumns = Integer.parseInt(r.nextLine());

            for (int j = 0; j < numOfColumns; j++) {

                showDataTypes(j);

                int opt = Integer.parseInt(r.nextLine());

                switch (opt) {
                    case 1:
                        format.add("name");
                        break;

                    case 2:
                        format.add("lName");
                        break;
                    case 3:
                        format.add("sex");
                        break;
                    case 4:
                        format.add("DOB");
                        break;
                    case 5:
                        format.add("departmentName");
                        break;
                    case 6:
                        format.add("randomNumber");

                        break;
                    case 7:
                        format.add("positiveRandomNumber");
                        break;
                    case 8:
                        format.add("address");
                        break;

                    case 9:
                        format.add("foreignKey");

                        List<String> existentTables = ig.getTablesNames();
                        System.out.println("Escoja la tabla de origen");

                        for (int k = 0; k < existentTables.size(); k++) {
                            System.out.println((k + 1) + " " + existentTables.get(k));
                        }

                        opt = Integer.parseInt(r.nextLine());
                        format.add(existentTables.get(opt - 1));
                        break;
                }

            }

            dateGenerated.addAll(ig.generateData(tableName, numToRecords, format));
            dateGenerated.add("\n");

        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText("Datos generados exitosamente, ahora escoja\ncarpeta para exportar el archivo");

        alert.showAndWait();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        System.out.println(selectedDirectory.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(
                new FileWriter(new File(selectedDirectory.getAbsolutePath() + "/INSERTS.sql")));

        for (String s : dateGenerated) {
            bw.write(s + "\n");
        }

        bw.close();
      

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Operación exitosa");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Ver en carpeta de destino?");

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