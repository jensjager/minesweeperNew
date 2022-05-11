package com.example.minesweepernew;

import javafx.beans.property.SimpleBooleanProperty;

public class Ruudud {

    // Isendiväljad
    private boolean miin;
    private SimpleBooleanProperty nähtav;
    private int mitu;

    // Konstruktor
    public Ruudud(boolean miin) {
        this.miin = miin;
        this.nähtav = new SimpleBooleanProperty(false);
    }

    // Meetodid
    public boolean isNähtav() {
        return nähtav.getValue();
    }

    public SimpleBooleanProperty getNähtav() {
        return nähtav;
    }

    public void setNähtav() {
        this.nähtav.setValue(true);
    }

    public boolean isMiin() {
        return miin;
    }

    public void setMitu(int mitu) {
        this.mitu = mitu;
    }

    public int getMitu() {
        return mitu;
    }

    @Override
    public String toString() {
        return "";
    }
}
