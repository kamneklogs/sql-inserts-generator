package model;

import java.util.*;

public class InsertsGenerator {

    private static final String[] namesSeed = { "Andrea", "Camilo", "Andres", "Camila", "Luz", "Maria", "Laura",
            "Daniela", "Valentina", "Juan", "Sebastian", "David", "Martin", "Mateo", "Lucas", "Jose", "Alejandro",
            "Laura", "Carlos", "Paola", "Angela", "Natalia", "Nelson", "Anderson", "Juan", "Anibal", "Mario", "Danna",
            "Sofia" };

    private static final String[] lNamesSeed = { "Nuñez", "Cordoba", "Gutierrez", "Rodriguez", "Mosquera", "Angulo",
            "Saenz", "Cardona", "Corrales", "Caballero", "Triana", "Gaviria", "Valencia", "Bolivar", "Hurtado",
            "Villegas", "Lopez", "Cardona", "Villota", "Gonzales", "Romero", "Garcia", "Escobar", "Trujillo" };

    private static final String[] departmentNameSeed = { "Administrativo", "Ejecutivo", "Legal", "Produccion",
            "Marketing", "Contable", "Servicios de planta", "Recursos Humanos", "Financiero", "Logistica",
            "Comercial" };

    private static final String[] townNameSeed = { "Siempre Viva", "Castillo", "Aures", "New New York", "Springfield",
            "Ciudad Cordoba", "San Cristobal", "Jardin", "Lourdes", "AgnosVille", "AV. Panamericana", "Las Margaritas",
            "La Revolucion", "Las Lomas", "El Castillo", "Pance", "Siloe", "Terron Colorado", "Agua Blanca",
            "Bochalema" };

    private HashMap<String, List<String>> keysByTable;

    public InsertsGenerator() {
        keysByTable = new HashMap<String, List<String>>();
    }

    public List<String> generateData(String tableName, int numToGenerate, List<String> format, boolean pKRequired) {

        List<String> gens = new ArrayList<String>();
        Random ram = new Random();

        for (int i = 0; i < numToGenerate; i++) {
            String t = "";
            if (pKRequired) {

                String subKey = "" + (i < 10 ? ("00" + i) : (i < 100 ? ("0" + i) : (i)));

                String key = (tableName.substring(0, 3).toUpperCase() + subKey);

                if (!keysByTable.containsKey(tableName)) {
                    keysByTable.put(tableName, new ArrayList<String>());
                }
                keysByTable.get(tableName).add(key);

                t += "INSERT INTO " + tableName.toUpperCase() + " VALUES ( " + "'" + key + "'";
            } else {
                t += "INSERT INTO " + tableName.toUpperCase() + " VALUES ( ";
            }

            for (int j = 0; j < format.size(); j++) {

                if (pKRequired) {
                    t += ",";
                } else {
                    if (j > 0 && j < (format.size())) {
                        t += ",";
                    }
                }

                switch (format.get(j)) {
                    case "name":

                        t += "'" + namesSeed[(int) (ram.nextDouble() * namesSeed.length - 1)] + "'";

                        break;
                    case "lName":
                        t += "'" + lNamesSeed[(int) (ram.nextDouble() * lNamesSeed.length - 1)] + "'";

                        break;
                    case "DOB":
                        int year = (int) (ram.nextDouble() * 45) + 1960;
                        int month = 1 + (int) (ram.nextDouble() * 11);
                        int day = 1 + (int) (ram.nextDouble() * 30);

                        t += "TO_DATE('" + (day < 10 ? ("0" + day) : day) + "/" + (month < 10 ? ("0" + month) : month)
                                + "/" + year + "', 'dd/mm/yyyy' )";

                        break;
                    case "departmentName":
                        t += "'D. " + departmentNameSeed[(int) (ram.nextDouble() * departmentNameSeed.length - 1)]
                                + "'";

                        break;
                    case "randomNumber":
                        t += (ram.nextInt());

                        break;
                    case "positiveRandomNumber":
                        t += (int) (ram.nextDouble() * 100000000);
                        break;

                    case "address":
                        t += "'" + (int) (1000 + (ram.nextDouble() * 10000)) + " "
                                + townNameSeed[(int) (ram.nextDouble() * townNameSeed.length - 1)] + "'";
                        break;
                    case "sex":
                        t += "'" + (ram.nextBoolean() ? "M" : "F") + "'";
                        break;
                    case "foreignKey":

                        String foreignKeyOrigin = format.get(j + 1);
                        t += getForeignKey(foreignKeyOrigin);
                        j++;
                        break;
                    default:
                        t += "null";
                        break;
                }

            }

            t += " );";
            gens.add(t);
        }

        return gens;
    }

    private String getForeignKey(String tableName) {

        List<String> temp = keysByTable.get(tableName);
        Random r = new Random();

        return "'" + temp.get((int) (r.nextDouble() * (temp.size() - 1))) + "'";

    }

    public List<String> getTablesNames() {
        List<String> r = new ArrayList<>(keysByTable.keySet());
        return r;
    }

}