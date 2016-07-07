package cn.zjc.main;

/**
 * @author zhangjinci
 * @version 2016/7/7 19:34
 */

import cn.zjc.controller.RestController;
import cn.zjc.mvc.MVC;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends  Application{

    public static final String VERSION = "1.0";
    public static final String TITLE = "RestCilent";

    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;

    public MVC mvcEngine;

    private Scene scene;



    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle(TITLE + " V "+VERSION);
        stage.getIcons().add(new Image("/img/logo.jpeg"));

//        // create and initialize the connectivity
//        dbManager = DatabaseManager.getDefault();
//        dbManager.openConnection();
//        Log.display("Database initialized");

        mvcEngine = new MVC((e)->{
            scene = new Scene(e, WIDTH, HEIGHT);
//            scene.getStylesheets().clear();
//            scene.getStylesheets().add("css/style.css");
            stage.setScene(scene);

        });
        // mvcEngine.setController(new MainController(WIDTH, HEIGHT));
        mvcEngine.setController(new RestController());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
