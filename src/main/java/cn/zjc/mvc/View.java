package cn.zjc.mvc;

import cn.zjc.view.DialogBackground;
import javafx.scene.layout.Pane;

/**
 * © Biodiscus.net 2014, Robin
 */
public class View extends Pane {
    // The pane the FXML will be added to
    public Pane fxmlPane;

    // For dialogs we need a background
    // If it's null than there is no background
    public DialogBackground dialogBackground;

    public View() {}

    public View(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
}
