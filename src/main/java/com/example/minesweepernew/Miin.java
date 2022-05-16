package com.example.minesweepernew;

public class Miin extends Ruudud {

    // Konstruktor
    public Miin() {
        super(true);
    }

    // Ülekate
    @Override
    public String toString() {
        if (isNähtav()) return "✸";
        return super.toString();
    }
}