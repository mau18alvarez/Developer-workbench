package UI.custom;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by josea on 6/15/2017.
 */
public class NodesPane extends Pane {

    private final int GAP = 100;

    public NodesPane(){

    }

    public void addNode(DBTable dNode){
        dNode.setLayoutY(GAP);
        DBTable CNode = new DBTable("");
        for(Node node : this.getChildren()){
            if(dNode != node) {
                DBTable tb;
                try{
                    tb = ((DBTable)(node));
                }catch (Exception e){continue;}
                if(!tb.name.matches("")) {
                    if (checkCollision(dNode, tb)) {
                        dNode.setLayoutX(tb.getLayoutX() + tb.getPrefWidth()+ GAP);
                        CNode = tb;
                    }
                }
            }
        }
        if(CNode.name !=""){
            connect(CNode,dNode);
        }
        this.getChildren().add(dNode);

    }

    public void addNodes(String node){
        String[] dNodes = node.split("\\^");
        DBTable newDNode = new DBTable("DiskNode");
        newDNode.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: cyan;");
        for(String dNode : dNodes){
            String[] nodeAttrs = dNode.split(",");
            if(!nodeAttrs[0].matches("")) {
                newDNode.addTableAttribute(nodeAttrs[0]);
            }
        }
        addNode(newDNode);
    }

    public boolean checkCollision(DBTable a, DBTable b) {
        if(a.getBoundsInParent().intersects(b.getBoundsInParent()))
        {
            return true;
        }
        return false;
    }

    private void connect(DBTable table1, DBTable table2){
        BoundLine line1 = new BoundLine(table1.getLayoutX()+table1.getPrefWidth()/2,table1.getLayoutY(),table1.getLayoutX()+table1.getPrefWidth()/2,GAP/2);
        BoundLine line2 = new BoundLine(table2.getLayoutX()+table2.getPrefWidth()/2,table2.getLayoutY(),table2.getLayoutX()+table2.getPrefWidth()/2,GAP/2);
        BoundLine line3 = new BoundLine(table1.getLayoutX()+table1.getPrefWidth()/2,GAP/2,table2.getLayoutX()+table2.getPrefWidth()/2,GAP/2);

        this.getChildren().addAll(line1,line2,line3);
    }

}
