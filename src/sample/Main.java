package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;
    public static int width = 1080;
    public static int height = 720;

    @Override
    public void start(Stage stage) {
        MainMenu mm = new MainMenu();
        primaryStage = stage;
        primaryStage.setTitle("Checkers");
        primaryStage.setResizable(false);
        Pane root = mm.createRoot();
        primaryStage.setScene(new Scene(root,width,height));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
