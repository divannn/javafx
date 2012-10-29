package chart;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Cross extends Group {
    private Line vLine = new Line();
    private Line hLine = new Line();

    public Cross() {
        setAutoSizeChildren(false);
        getChildren().addAll(vLine, hLine, new Label("jjjj"));
        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.err.println("###mouse");
            }
        });
    }
}
