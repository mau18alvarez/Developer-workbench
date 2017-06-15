package UI.custom;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by josea on 6/15/2017.
 */
class BoundLine extends Line {
    BoundLine(double startX, double startY, double endX, double endY) {
        startXProperty().bind(new SimpleDoubleProperty(startX));
        startYProperty().bind(new SimpleDoubleProperty(startY));
        endXProperty().bind(new SimpleDoubleProperty(endX));
        endYProperty().bind(new SimpleDoubleProperty(endY));
        setStrokeWidth(2);
        setStroke(Color.GREEN.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        setMouseTransparent(true);
    }
}