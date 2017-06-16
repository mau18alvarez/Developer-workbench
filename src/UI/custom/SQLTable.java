package UI.custom;

import java.util.List;

/**
 * Created by Usuario on 15/06/2017.
 */
public class SQLTable {
    private List<String> columns;
    private List<List<String>> values;
    private String title;

    public SQLTable(String title, List<String> columns, List<List<String>> values) {
        this.title = title;
        this.columns = columns;
        this.values = values;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public List<String> getValues(int i) {
        return values.get(i);
    }

    public String getValues(int i, int j) {
        return values.get(i).get(j);
    }

    public String getTitle() {
        return title;
    }
}
