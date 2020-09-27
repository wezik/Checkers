package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenu {

    private int width = Main.width;
    private int height = Main.height;

    public void run() {
        Main.primaryStage.setScene(new Scene(createRoot(),width,height));
    }

    public Pane createRoot() {
        Pane pane = new Pane();
        pane.setPrefSize(width,height);
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Button startGame = new Button();
        startGame.setText("Start new game");
        setVisualsFor(startGame);
        startGame.setOnAction(e -> new Checkers(type).start());
        Button options = new Button();
        options.setText("Options");
        options.setOnAction(e -> optionsOnClick());
        setVisualsFor(options);
        Button exit = new Button();
        exit.setText("Exit");
        exit.setOnAction(e-> Main.primaryStage.close());
        setVisualsFor(exit);
        vBox.getChildren().addAll(startGame,options,exit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        vBox.setPrefSize(300,height);
        pane1.setPrefSize(width/2-150,height);
        pane2.setPrefSize(width/2-150,height);
        hBox.setPrefSize(width,height);
        hBox.getChildren().addAll(pane1,vBox,pane2);
        pane.getChildren().add(hBox);
        pane.setStyle("-fx-background-color: #9D6B53");
        return pane;
    }

    private void setVisualsFor(Button button) {
        button.setOnMouseEntered(e -> button.setTextFill(Color.valueOf("#774936")));
        button.setOnMouseExited(e-> button.setTextFill(Color.valueOf("#E6B8A2")));
        button.setTextFill(Color.valueOf("#E6B8A2"));
        button.setFont(Font.font("lato", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,35));
        button.setStyle("-fx-background-color: transparent");
    }
    private Stage optionsStage;
    private PlayerType type = PlayerType.HUMAN;

    private void optionsOnClick() {
        if (optionsStage==null) {
            optionsStage = new Stage();
            HBox hbox = new HBox();
            hbox.setPrefSize(300,150);
            VBox leftSide = new VBox();
            VBox rightSide=  new VBox();
            leftSide.setPrefSize(180,150);
            leftSide.setAlignment(Pos.CENTER);
            rightSide.setPrefSize(120,150);
            rightSide.setAlignment(Pos.CENTER_LEFT);
            ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Human player","Bot"));
            PlayerType[] types = new PlayerType[]{PlayerType.HUMAN,PlayerType.BOT};
            cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue ov, Number value, Number new_value) {
                    type = types[new_value.intValue()];
                }
            });
            cb.setTooltip(new Tooltip("Select your opponent"));
            cb.setPrefSize(150,20);
            Label playerLabel = new Label();
            playerLabel.setText("Versus");
            rightSide.getChildren().add(playerLabel);
            leftSide.getChildren().add(cb);
            hbox.setStyle("-fx-background-color: #9D6B53");
            hbox.getChildren().addAll(leftSide,rightSide);
            Scene scene = new Scene(hbox);
            optionsStage.setScene(scene);
            optionsStage.setResizable(false);
        }
        optionsStage.showAndWait();
    }
}
