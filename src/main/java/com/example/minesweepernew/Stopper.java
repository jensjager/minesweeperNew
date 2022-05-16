package com.example.minesweepernew;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;

public class Stopper {

    // Isendiväljad
    private Text parimAeg;
    private Text aeg;
    private Timeline lugeja;

    // Konstrukor
    public Stopper() {
        this.parimAeg = loeParim();
        this.aeg = new Text("00:00");

        // Luuakse Timeline isend, mis hakkab igal sekundil aeg teksti muutma
        this.lugeja = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int aeg = 1 + aegInt(this.aeg.getText());

            String uusAeg = aegString(aeg);

            setAeg(uusAeg);
        }));
        this.lugeja.setCycleCount(Timeline.INDEFINITE);
    }

    // Meetodid

    // Meetod loeb failist sisse parima aja
    private Text loeParim() {
        Text parim = new Text();
        try (DataInputStream d = new DataInputStream(new FileInputStream("parim.dat"))) {
            int sekundid = d.readInt();
            String aeg = aegString(sekundid);
            parim.setText("Parim aeg: " + aeg);

        } catch (IOException e) {
            // Kui failist lugemisel tekkis mingi probleem, siis märgitakse,
            // et parim aeg puudub
            parim.setText("Parim aeg: puudub");
        }
        return parim;
    }

    // Meetod teisendab aja kujul "00:00" täisarvuks
    private int aegInt(String sõne) {
        String[] osad = sõne.split(":");

        return Integer.parseInt(osad[0]) * 60 +
                Integer.parseInt(osad[1]);
    }

    // Meetod teisendab täisarvust ajaühiku kujule "00:00"
    private String aegString(int sekundid) {
        StringBuilder sb = new StringBuilder(5);

        if (sekundid / 60 < 10)
            sb.append(0);
        sb.append(sekundid / 60).append(':');
        if (sekundid % 60 < 10)
            sb.append(0);
        sb.append(sekundid % 60);

        return sb.toString();
    }

    public void start() {
        this.lugeja.play();
    }

    public void stop() {
        this.lugeja.stop();
    }

    // Meetod otsustab, kas salvestada uut aega või ei
    public void salvestaParim(int suurus) {
        if (suurus > 9) {
            int aeg = aegInt(this.aeg.getText());
            String parim = this.parimAeg.getText().split(" ")[2];

            // Kui välja suurus on üle 9 ning võidu aeg on parimast kiirem
            if (parim.equals("puudub") || aeg < aegInt(parim)) {
                String uusParim = aegString(aeg);
                setParimAeg("Parim aeg: " + uusParim);
                salvesta(aeg);
            }
        }
    }

    // Meetod salvestab uue parima aja faili
    private void salvesta(int aeg) {
        try (DataOutputStream d = new DataOutputStream(new FileOutputStream("parim.dat"))) {
            d.writeInt(aeg);
        } catch (IOException e) {
            // Kui faili kirjutades tekib erind, lõpetatakse programmi töö
            throw new RuntimeException(e);
        }
    }

    public void restart() {
        this.aeg.setText("00:00");
    }

    public void setParimAeg(String parimAeg) {
        this.parimAeg.setText(parimAeg);
    }

    public void setAeg(String aeg) {
        this.aeg.setText(aeg);
    }

    // Meetod tagastab parima aja Text isendi
    public Text getParimAeg() {
        return parimAeg;
    }

    // Meetod tagastab aja Text isendi
    public Text getAeg() {
        return aeg;
    }
}
