package com.example.minesweepernew;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        alusta(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void alusta(Stage stage){

        int suurus = suurus();

        // Loome programmi maastiku
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        ObservableList<ColumnConstraints> colCconstraints = gridPane.getColumnConstraints();
        colCconstraints.clear();
        for(int col = 0; col < 10; col++){
            ColumnConstraints c = new ColumnConstraints();
            c.setHalignment(HPos.CENTER);
            c.setHgrow(Priority.ALWAYS);
            colCconstraints.add(c);
        }
        ObservableList<RowConstraints> rowCconstraints = gridPane.getRowConstraints();
        rowCconstraints.clear();
        for(int row = 0; row < 10; row++){
            RowConstraints c = new RowConstraints();
            c.setValignment(VPos.CENTER);
            c.setVgrow(Priority.ALWAYS);
            rowCconstraints.add(c);
        }

        Väli väli = new Väli(10,20,gridPane);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    private static int suurus(){
        Stage stage = new Stage();
        TilePane tilePane = new TilePane();
        //TextInputControl
        Label l = new Label();
        TextInputDialog kasutajaSisend = new TextInputDialog("Sisestage soovitud välja suurus (<=20): ");
        Button nupp = new Button("OK");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                // show the text input dialog
                kasutajaSisend.showAndWait();

                // set the text of the label
                l.setText(kasutajaSisend.getEditor().getText());
            }
        };
        nupp.setOnAction(event);
        Scene sc = new Scene(tilePane, 500, 300);
        stage.setScene(sc);
        stage.show();
        return Integer.parseInt(l.getText());
    }

    public static void end(GridPane gridPane){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setTitle("Uus mäng?");
        alert.setContentText("Kaotasite! Kas soovite uuesti mängida?");
        ButtonType okButton = new ButtonType("Jah", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Ei", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                gridPane.getChildren().clear();
                Väli väli = new Väli(10,20,gridPane);
            } else{
                System.exit(0);
            }
        });
    }
}