package Tables;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.*;

/**
 * Created by mauri on 6/8/2017.
 */

public class DynamicTableView implements Initializable {


    private static final int Num_Colum = 4;
    private static final int Num_Fila = Metada_Parser.rows-1;
    @FXML  TableView<ObservableList<String>> tableView = new TableView<>();


    public void initialize(URL location, ResourceBundle resources) {

        Metada_Parser.parse();
        TestDataGenerator dataGenerator = new TestDataGenerator();

        // Rows and Columns Dynamic

        List<String> Colum_Names = Arrays.asList("Name", "Data Type", "IsRequired", "Referencing");
        List<String> information = dataGenerator.getNext(Num_Colum);

        for (int i = 0; i < information.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    Colum_Names.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))

            );



            column.setSortable(false);
            tableView.getColumns().add(column);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

        // add data
        for (int i = 0; i < Num_Fila; i++) {
            tableView.getItems().add(FXCollections.observableArrayList(TestDataGenerator.table_data));
            dataGenerator.getNext(Num_Colum);
        }

    }


    private static class TestDataGenerator {

        private static final String[] DATA = Metada_Parser.Table_Data.split(",");
        private int curWord = 0;
        public static List<String> table_data = new ArrayList<>();


        List<String> getNext(int nWords) {
           List<String> words = new ArrayList<>();
            for (int i = 0; i < nWords; i++) {
                if (curWord == Integer.MAX_VALUE) {
                    curWord = 0;
                }

                words.add(DATA[curWord % DATA.length]);
                curWord++;
            }
            table_data = words;
            return words;

        }
    }
}