package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class InsertsGenerator {

    private File file;

    public InsertsGenerator(File file) {

        this.file = file;
    }

    public void generate(ArrayList<String> data, String tableName) throws IOException {

        BufferedWriter insertsWriter = new BufferedWriter(new FileWriter(file));

        // INSERT INTO Staff VALUES ('SL21', 'Juan David', 'Gomez','manager',
        // 'M','1-Oct-20', 30000, 'B005');

        for (String string : data) {
            insertsWriter.write("INSERT INTO " + tableName + " VALUES (" + string + ");\n");
        }

        insertsWriter.close();

    }

}
