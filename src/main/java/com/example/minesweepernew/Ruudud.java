package com.example.minesweepernew;

public class Ruudud {

    // Isendiväljad
    private boolean miin;
    private boolean nähtav = false;
    private int mitu;

    // Konstruktor
    public Ruudud(boolean miin) {
        this.miin = miin;
    }

    // Meetodid
    public boolean isNähtav() {
        return nähtav;
    }

    public void setNähtav() {
        this.nähtav = true;
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
        return " ";
    }
}
