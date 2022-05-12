package com.example.minesweepernew;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Kõigepealt avatakse tutvustav aken koos tekstiväljaga, kus küsitakse soovitud välja suurust
        VBox juur = new VBox();
        juur.setPadding(new Insets(5, 5, 5, 5));
        juur.setSpacing(10);

        Text juhised = new Text("""
                Minesweeper
                                
                Mängijale on ette antud varjatud väli, kus osad ruudud on miinid.
                Mängu eesmärgiks on nähtavaks muuta kõik tühjad ruudud,
                kasutades ära fakti, et tühjad ruudud näitavad mitut miini nad puudutavad.
                Mängija võidab mängu, kui kõik tühjad ruudud on nähtavad.
                                
                Parim aeg salvestatakse siis, kui välja suurus on suurem kui 9""");
        juhised.setStyle("-fx-font-size: 14");

        Text küsimus = new Text("Sisesta soovitud välja suurus (2 - 30)");
        küsimus.setStyle("-fx-font-size: 14");

        TextField tekstiväli = new TextField();
        tekstiväli.setMaxWidth(200);

        Button kinnitus = new Button("OK");
        kinnitus.setOnMouseClicked(event -> {
            // Peale nupuvajutust üritatakse tekst muuta täisarvuks ning alustada mäng
            try {
                int suurus = Integer.parseInt(tekstiväli.getText());

                // Kui suurus ei jää lubatud vahemikku, visatakse ValeNumberErind
                if (suurus < 2 || 30 < suurus)
                    throw new ValeNumberErind("Vigane sisend, sisestasite: " + suurus);

                alustaMäng(suurus);
                stage.close();

            } catch (NumberFormatException e) {
                // Kui tekstiväljal leitakse tähemärk
                küsimus.setText("Sisesta soovitud välja suurus (2 - 30)\n" +
                        "Vigane sisend, sisestasite: \"" + tekstiväli.getText() + "\"");

                // Lavale antakse uus suurus koos uue minimaalse suurusega
                stage.sizeToScene();
                stage.setMinHeight(stage.getHeight());
                stage.setMinWidth(stage.getWidth());

            } catch (ValeNumberErind e) {
                // Kui saadud suurus ei jää lubatud vahemikku
                küsimus.setText("Sisesta soovitud välja suurus (2 - 30)\n" +
                        e.getMessage());

                // Lavale antakse uus suurus koos uue minimaalse suurusega
                stage.sizeToScene();
                stage.setMinHeight(stage.getHeight());
                stage.setMinWidth(stage.getWidth());
            }
        });

        juur.getChildren().addAll(juhised, küsimus, tekstiväli, kinnitus);

        Scene sc = new Scene(juur);
        stage.setTitle("Minesweeper");
        stage.setScene(sc);
        stage.show();

        // Kitsendused teevad nii, et akent ei saa väiksemaks teha kui väli
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    public void alustaMäng(int suurus) {
        // Alustatakse põhimänguga
        Stage stage = new Stage();
        BorderPane juur = new BorderPane();
        juur.setPadding(new Insets(5, 5, 5, 5));

        // Uue mängu nupu ning stopperi jaoks
        HBox hBox = new HBox(30);
        hBox.setPadding(new Insets(5, 0, 5, 0));

        // Rakenduses nähtav mänguväli
        GridPane gp = new GridPane();
        // GridPane-le luuakse rea ja veeru kitsendused, et mäng käituks normaalselt akna suuruse muutumisele
        looKitsendused(gp, suurus);

        // Luuakse stopperi isend aja võtmiseks
        Stopper stopper = new Stopper();

        // Luuakse uus mänguväli
        Väli väli = new Väli(suurus);
        // GridPane-le luuakse mänguväljale vastav ruudustik
        looRuudustik(väli, gp, stage, stopper);

        Button uusMäng = new Button("Uus mäng");
        uusMäng.setOnMouseClicked(event -> küsiUusMäng(gp, "", stage, stopper));

        hBox.getChildren().addAll(uusMäng, stopper.getParimAeg(), stopper.getAeg());

        juur.setTop(hBox);
        juur.setCenter(gp);

        Scene sc = new Scene(juur);
        stage.setScene(sc);
        stage.setTitle("Minesweeper");
        stage.show();

        // Stopper alustab tööd
        stopper.start();

        // Kitsendused teevad nii, et akent ei saa väiksemaks teha kui väli
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void looKitsendused(GridPane gp, int suurus) {
        // GridPane-le luuakse rea ja veeru kitsendused, et mäng käituks normaalselt akna suuruse muutumisele
        ObservableList<RowConstraints> reaKitsendus = gp.getRowConstraints();
        reaKitsendus.clear();

        ObservableList<ColumnConstraints> veeruKitsendus = gp.getColumnConstraints();
        veeruKitsendus.clear();

        for (int i = 0; i < suurus; i++) {
            RowConstraints rida = new RowConstraints();
            rida.setValignment(VPos.CENTER);
            rida.setVgrow(Priority.ALWAYS);
            reaKitsendus.add(rida);

            ColumnConstraints veerg = new ColumnConstraints();
            veerg.setHalignment(HPos.CENTER);
            veerg.setHgrow(Priority.ALWAYS);
            veeruKitsendus.add(veerg);
        }
    }

    private void küsiUusMäng(GridPane gp, String sõnum, Stage peaStage, Stopper stopper) {
        // Avatakse uus aken, millega luuakse uus mänguväli soovitud suurusega
        Stage stage = new Stage();

        // Kitsendus, mis ei lase samal ajal tegeleda mänguaknaga
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(peaStage);

        VBox juur = new VBox();
        juur.setPadding(new Insets(5, 5, 5, 5));
        juur.setSpacing(10);

        String küsimuseTekst;

        if (sõnum.equals(""))
            küsimuseTekst = "Sisesta soovitud välja suurus (2 - 30)";
        else
            küsimuseTekst = sõnum + "\n\nSisesta soovitud välja suurus (2 - 30)";

        Text küsimus = new Text(küsimuseTekst);
        küsimus.setStyle("-fx-font-size: 14");

        TextField tekstiväli = new TextField();
        tekstiväli.setMaxWidth(200);

        Button kinnitus = new Button("OK");
        kinnitus.setOnMouseClicked(event1 -> {
            try {
                // Peale nupuvajutust üritatakse tekst muuta täisarvuks ning luua uus mänguväli
                int suurus = Integer.parseInt(tekstiväli.getText());

                // Kui suurus ei jää lubatud vahemikku, visatakse ValeNumberErind
                if (suurus < 2 || 20 < suurus)
                    throw new ValeNumberErind("Vigane sisend, sisestasite: " + suurus);

                // Luuakse uus väli uue suurusega
                Väli uusVäli = new Väli(suurus);
                // GridPane eelnevad ruudud kustutakse
                gp.getChildren().clear();

                // Luuakse rea- ja veerukitsendused ning uus nuppudest ruudustik
                looKitsendused(gp, suurus);
                looRuudustik(uusVäli, gp, peaStage, stopper);

                // Pealavale antakse uus suurus koos uue minimaalse suurusega
                peaStage.sizeToScene();
                peaStage.setMinHeight(peaStage.getHeight());
                peaStage.setMinWidth(peaStage.getWidth());
                stage.close();

                // Stopper alustab algusest
                stopper.restart();
                stopper.start();

            } catch (NumberFormatException e) {
                // Kui tekstiväljal leitakse tähemärk
                küsimus.setText(küsimuseTekst +
                        "\nVigane sisend, sisestasite: \"" + tekstiväli.getText() + "\"");

                // Lavale antakse uus suurus koos uue minimaalse suurusega
                stage.sizeToScene();
                stage.setMinHeight(stage.getHeight());
                stage.setMinWidth(stage.getWidth());

            } catch (ValeNumberErind e) {
                // Kui saadud suurus ei jää lubatud vahemikku
                küsimus.setText(küsimuseTekst + "\n" +
                        e.getMessage());

                // Lavale antakse uus suurus koos uue minimaalse suurusega
                stage.sizeToScene();
                stage.setMinHeight(stage.getHeight());
                stage.setMinWidth(stage.getWidth());
            }
        });

        juur.getChildren().addAll(küsimus, tekstiväli, kinnitus);

        Scene sc = new Scene(juur);
        stage.setTitle("Minesweeper");
        stage.setScene(sc);
        stage.show();

        // Kitsendused teevad nii, et akent ei saa väiksemaks teha
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void looRuudustik(Väli väli, GridPane gp, Stage peaStage, Stopper stopper) {
        // GridPane-le luuakse nuppudest ruudustik, mis vastav mänguväljale
        for (int i = 0; i < väli.getSuurus(); i++) {
            for (int j = 0; j < väli.getSuurus(); j++) {
                int rida = i;
                int veerg = j;

                // Luuakse nupp koos kitsendustega
                Button nupp = new Button(väli.getRuut(rida, veerg).toString());
                nupp.setMinSize(30, 30);
                nupp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                nupp.setPrefSize(30, 30);
                nupp.setStyle("-fx-background-color: #CACACA; -fx-border-color: #838383;" +
                        "-fx-border-width: 1px; -fx-background-radius: 0");

                nupp.setOnMouseClicked(event -> {
                    // Väljal avaldatakse ruut või ruudud
                    väli.avaldaRuut(rida, veerg);

                    // Kui avaldatud ruut on miin, siis mängija kaotab mängu
                    if (väli.getRuut(rida, veerg).isMiin()) {
                        // Stopper peatatakse
                        stopper.stop();

                        väli.avaldaKõik();
                        küsiUusMäng(gp, "Kaotasite, kuna läksite miini pihta!", peaStage, stopper);
                    }

                    // Kui varjatud mittemiine enam ei leidu, siis on mängija võitnud
                    else if (väli.eiLeiduVarjatud()) {
                        // Stopper peatatakse
                        stopper.stop();

                        // Kui välja suurus on suurem kui 9 ning tehti aja poolest rekord, siis see salvestatakse
                        stopper.salvestaParim(väli.getSuurus());
                        küsiUusMäng(gp, "Võitsite, palju õnne!\nTeie aeg: " + stopper.getAeg().getText(), peaStage, stopper);
                    }
                });

                // Lisatakse kuulaja, mis muudab nupu teksti siis, kui mänguväljal ruut avaldatakse
                väli.getRuut(rida, veerg).getNähtav().addListener((o, v, n) -> {
                    nupp.setStyle("-fx-background-color: #AEAEAE; -fx-border-color: #838383;" +
                            "-fx-border-width: 1px; -fx-background-radius: 0");
                    nupp.setText(väli.getRuut(rida, veerg).toString());
                });

                // Nupp lisatakse GridPane-le
                gp.add(nupp, i, j);
            }
        }
    }
}