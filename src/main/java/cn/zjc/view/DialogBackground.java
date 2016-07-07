package cn.zjc.view;

import cn.zjc.mvc.View;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The DialogBackground will create a blakish background, when adding a controller this class will be used as a background.
 *
 * © 2014, Biodiscus.net Robin
 */
public class DialogBackground extends View {

    public DialogBackground(Scene scene) {
        double width = scene.getWidth();
        double height = scene.getHeight();

        Color color = new Color(0, 0, 0, 0.5);

        Rectangle rectangle = new Rectangle(width, height, color);
        getChildren().add(rectangle);
    }
}
