package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class InsertsGenerator {

    private static final String[] namesSeed = { "Andrea", "Camilo", "Andres", "Camila", "Luz", "Maria", "Laura",
            "Daniela", "Valentina", "Juan", "Sebastian", "David", "Martin", "Mateo", "Lucas", "Jose", "Alejandro",
            "Laura", "Carlos", "Paola" };
    private static final String[] lNamesSeed = { "Nuñez", "Cordoba", "Gutierrez", "Rodriguez", "Mosquera", "Angulo",
            "Saenz", "Cardona", "Corrales", "Caballero", "Triana", "Gaviria", "Valencia", "Bolivar", "Hurtado",
            "Villegas", "Lopez", "Cardona" };

    private static final String[] departmentNameSeed = { "Administrativo", "Ejecutivo", "Legal", "Produccion",
            "Marketing", "Contable", "Servicios de planta" };

    private static final String[] townNameSeed = { "Siempre Viva", "Castillo", "Aures", "New New York", "Springfield",
            "Ciudad Cordoba", "San Cristobal", "Jardin", "Lourdes", "AgnosVille", "AV. Panamericana" };

    private HashMap<String, HashSet<String>> keysByTable;

    public InsertsGenerator() {
        keysByTable = new HashMap<String, HashSet<String>>();
    }

    // Format example --->>> numRecsToGenerateByTable=int, tableName=varchar,
    // name=name, lastName=lName, age= legalAge(1960 a 2005 legal), departmentName
    public List<String> generateData(String tableName, int numToGenerate, List<String> format) {

        List<String> gens = new ArrayList<String>();
        Random ram = new Random();

        for (int i = 0; i < numToGenerate; i++) {
            String t = "";

            String subKey = "" + (i < 10 ? ("00" + i) : (i < 100 ? ("0" + i) : (i)));

            String key = (tableName.substring(0, 3).toUpperCase() + subKey);

            if (!keysByTable.containsKey(tableName)) {
                keysByTable.put(tableName, new HashSet<String>());
            }
            keysByTable.get(tableName).add(key);

            t += "INSERT INTO " + tableName.toUpperCase() + " VALUES ( " + "'" + key + "'";
            for (int j = 0; j < format.size(); j++) {
                switch (format.get(j)) {
                    case "name":
                        t += ", '" + namesSeed[(int) (ram.nextDouble() * namesSeed.length - 1)] + "'";

                        break;
                    case "lName":
                        t += ", " + "'" + lNamesSeed[(int) (ram.nextDouble() * lNamesSeed.length - 1)] + "'";

                        break;
                    case "DOB":

                        int year = (int) (ram.nextDouble() * 45) + 1960;
                        int month = 1 + (int) (ram.nextDouble() * 11);
                        int day = 1 + (int) (ram.nextDouble() * 30);

                        t += ", " + "'" + year + "-" + (month < 10 ? ("0" + month) : month) + "-"
                                + (day < 10 ? ("0" + day) : day) + "'";

                        break;
                    case "departmentName":
                        t += ", " + "'D. "
                                + departmentNameSeed[(int) (ram.nextDouble() * departmentNameSeed.length - 1)] + "'";

                        break;
                    case "ramdomNumber":
                        t += ", " + (int) (ram.nextInt());

                        break;
                    case "positiveRamdomNumber":
                        t += ", " + (int) (ram.nextDouble() * 100000000);
                        break;

                    case "address":
                        t += ", " + "'" + (int) (1000 + (ram.nextDouble() * 10000)) + " "
                                + townNameSeed[(int) (ram.nextDouble() * townNameSeed.length - 1)] + "'";
                        break;
                    case "sex":
                        t += ", " + "'" + (ram.nextBoolean() ? "male" : "female") + "'";
                        break;
                    default:
                        t += ", " + "null";
                        break;
                }

            }
            t += " );";
            gens.add(t);
        }

        return gens;
    }

}