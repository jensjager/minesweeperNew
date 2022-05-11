package com.example.minesweepernew;

public class TühiRuut extends Ruudud {

    // Konstruktor
    public TühiRuut() {
        super(false);
    }

    // Ülekate
    @Override
    public String toString() {
        if (isNähtav() && getMitu() > 0)
            return String.valueOf(getMitu());
        else return super.toString();
    }
}
