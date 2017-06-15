package UI.custom;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

/**
 * Created by josea on 6/14/2017.
 */
public class CustomStructurePane extends Pane {

    private int rowNumber = 0;
    private int columnNumber = 0;

    public CustomStructurePane(){

    }

    public boolean checkCollision(DBTable a, DBTable b) {
        if(a.getBoundsInParent().intersects(b.getBoundsInParent()))
        {
            System.out.println("mierda");
            return true;
        }
        return false;
    }

    public void addTable(DBTable table){
        this.getChildren().add(table);
        for(Node node : this.getChildren()){
            if(table != node) {
                DBTable tb = new DBTable("");
                try{
                    tb = ((DBTable)(node));
                }catch (Exception e){continue;}
                if(!tb.name.matches("")) {
                    if (checkCollision(table, tb)) {
                        table.setLayoutX(node.getLayoutX() + ((DBTable) node).getPrefWidth() + 50);
                    }
                }
            }
        }
    }

    public void addTable(String table){
        String[] strArr = table.split(",");
        DBTable newDBTable = new DBTable(strArr[0]);
        int k = doesTableExists(strArr[0]);
        if( k != -1){
            newDBTable = (DBTable)this.getChildren().get(k);
        }
        if(doesTableExists(strArr[0]) == -1) {
            this.addTable(newDBTable);
        }
        for(int i = 1; i < strArr.length ; i++){
            String[] attrArr = strArr[i].split("-");
            String tabAttr = "";
            for(String str : attrArr){

                if(str.contains(":")){
                    String[] reffArr = str.split(":");
                    if(reffArr[0].contains("!")){
                        tabAttr += reffArr[0].substring(1) + " NOT NULL " ;
                    }else{
                        tabAttr += reffArr[0] +" ";
                    }
                    String[] arr = reffArr[1].split("\\.");
                    tabAttr += " REFERENCES " + arr[0] + " ( " + arr[1] + " ) ";
                    newDBTable.dependency = arr[0];
                    int j = doesTableExists(arr[0]);
                    if( j == -1){
                        DBTable table2 = new DBTable(arr[0]);
                        this.addTable(table2);
                    }
                    j = doesTableExists(arr[0]);
                    if( j != -1){
                        connect(newDBTable,(DBTable) this.getChildren().get(j));
                    }
                }
                else if(str.contains("!")){
                    tabAttr += str.substring(1) + " NOT NULL " ;
                }
                else{
                    tabAttr += str +" ";
                }
            }
            newDBTable.addTableAttribute(tabAttr);
        }
    }

    public int doesTableExists(String name){
        for(Node node : this.getChildren()){
            DBTable table = new DBTable("");
            try{
                table = ((DBTable)(node));
            }catch (Exception e){}
            if(table.name.matches(name)){
                return this.getChildren().indexOf(node);
            }
        }
        return -1;
    }

    public void connect(DBTable table1, DBTable table2){
        System.out.println("Conect: "+table1.name+" with "+table2.name);
        System.out.println(table1.getLayoutX());
        BoundLine boundLine = new BoundLine(table1.getLayoutX() + table1.getPrefWidth(),
                table1.getBoundsInParent().getMaxY() + 100,
                table2.getBoundsInParent().getMaxX(),
                table2.getBoundsInParent().getMaxY() + 100);
        Anchor start    = new Anchor(Color.PALEGREEN, boundLine.startXProperty(), boundLine.startYProperty());
        Anchor end      = new Anchor(Color.TOMATO,    boundLine.endXProperty(),   boundLine.endYProperty());
        this.getChildren().addAll(boundLine,start,end);

    }

}
