package Tables;

import java.util.ArrayList;

public class Metada_Parser {

    public static String Data = "MIERDA,Fname-!VARCHAR(15),Pene-VARCHAR(20):REFERENCE(CURSO.ID),Panochito-!VARCHAR(10)";
    public static String Table_Name = "";
    public static String Table_Data = "";
    public static int rows= 0;

    public static void parse() {

        String[] parts = Data.split(",");
        Table_Name = parts[0];
        String[] array = Data.split(",");
        rows = array.length - 1;

        for (int i = 1; i < array.length; i++) {
            Data = array[i];
            String[] array2 = Data.split("-");
            Table_Data += String.valueOf(array2[0]+",");

            if (String.valueOf(array2[1].charAt(0)).equals("!")){
                Table_Data += String.valueOf(array2[1].substring(1)+",");
                Table_Data += String.valueOf("FALSE"+",");
                Table_Data += String.valueOf("FALSE"+",");
            }else{
                String[] array3 =  array2[1].split(":REFERENCE");
                Table_Data += String.valueOf("TRUE"+",");
                Table_Data += String.valueOf(array3[0]+",");
                Table_Data += String.valueOf(array3[1]+",");
            }
        }

    }
}

