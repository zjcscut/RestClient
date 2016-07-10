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

    private static final String VERSION = "1.0";
    private static final String TITLE = "PostDoge";

    private static final int WIDTH = 1220;
    private static final int HEIGHT = 930;

    private  MVC mvcEngine;

    private Scene scene;



    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
//		stage.setMinWidth(800);
//		stage.setMinWidth(1200);
//		stage.setFullScreen(false);
//		stage.setMaximized(false);
        stage.setTitle(TITLE + " V "+VERSION);
        stage.getIcons().add(new Image("/img/logo.jpeg"));


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
