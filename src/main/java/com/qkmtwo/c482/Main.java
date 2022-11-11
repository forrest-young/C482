package com.qkmtwo.c482;

import helper.FXUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Forrest Young
 */
public class  Main extends Application {
    /**
     *
     * @param stage the stage to be set
     * @throws IOException standard
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXUtil.loadView(stage, "mainView.fxml");
    }

    /**
     *
     * @param args the args passed to the program
     */
    public static void main(String[] args) {
        launch();
    }
}