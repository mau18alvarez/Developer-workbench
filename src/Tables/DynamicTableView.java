package Tables;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

/**
 * Created by mauri on 6/8/2017.
 */

public class DynamicTableView extends Application {

    private static final int Num_Colum = 4;
    private static final int Num_Fila = 3;

    public void start(Stage stage) throws Exception {

        TestDataGenerator dataGenerator = new TestDataGenerator();
        TableView<ObservableList<String>> tableView = new TableView<>();

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
            tableView.getColumns().add(column);
        }

        // add data
        for (int i = 0; i < Num_Fila; i++) {
            tableView.getItems().add(
                    FXCollections.observableArrayList(
                            dataGenerator.getNext(Num_Colum)
                    )
            );
        }

        tableView.setPrefHeight(200);

        Scene scene = new Scene(tableView);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class TestDataGenerator {
        private static final String[] DATA = "Mauricio. String. Varchar15. NOSEQUE. Andres. String2. Varchar125. NOSE2QUE. AndrWEQes. StEQWring. VarrcQWEhar52. TIENEELPENEGRANDE.".split(" ");

        private int curWord = 0;

        List<String> getNext(int nWords) {
            List<String> words = new ArrayList<>();

            for (int i = 0; i < nWords; i++) {
                if (curWord == Integer.MAX_VALUE) {
                    curWord = 0;
                }

                words.add(DATA[curWord % DATA.length]);
                curWord++;
            }

            return words;
        }
    }
}