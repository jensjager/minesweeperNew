package com.example.minesweepernew;

public class Väli {

    // Isendiväljad
    private Ruudud[][] väli;
    private int suurus;

    // Konstruktor
    public Väli(int suurus) {
        this.suurus = suurus;
        väli = new Ruudud[suurus][suurus];
        looVäli();
        väärtustaTühjad();
    }

    // Meetodid
    public Ruudud getRuut(int i, int j) {
        return väli[i][j];
    }

    public int getSuurus() {
        return suurus;
    }

    // Meetod loob väljale Miinid
    public void looVäli() {
        for (int i = 0; i < suurus; i++) {
            for (int j = 0; j < suurus; j++) {
                if (Math.random() < 0.15) {
                    väli[i][j] = new Miin();
                } else {
                    väli[i][j] = new TühiRuut();
                }
            }
        }
    }

    // Meetod väärtustab tühjad ruudud arvuga, mis näitab, mitu miinist naabrit neil on
    public void väärtustaTühjad() {
        for (int i = 0; i < suurus; i++) {
            for (int j = 0; j < suurus; j++) {
                if (väli[i][j].isMiin()) continue;
                väli[i][j].setMitu(leiaVäärtus(i, j));
            }
        }
    }

    // Meetod leiab indeksitel i ja j asuva elemendi kõrval asuvate miinide arvu
    public int leiaVäärtus(int i, int j) {
        int kokku = 0;
        for (int k = i - 1; k < i + 2 && k < suurus; k++) {
            for (int l = j - 1; l < j + 2 && l < suurus; l++) {
                if (k < 0) k = 0;
                if (l < 0) l = 0;
                if (k == i && l == j) continue;
                if (väli[k][l].isMiin()) kokku++;
            }
        }
        return kokku;
    }

    // Meetod muudab kõik ruudud nähtavaks
    public void avaldaKõik() {
        for (int i = 0; i < suurus; i++) {
            for (int j = 0; j < suurus; j++) {
                avaldaRuut(i, j);
            }
        }
    }

    // Meetod kontrollib varjatud tühjade ruutude olemasolu
    public boolean eiLeiduVarjatud() {
        for (int i = 0; i < suurus; i++) {
            for (int j = 0; j < suurus; j++) {
                if (!väli[i][j].isNähtav() && !väli[i][j].isMiin()) return false;
            }
        }
        return true;
    }

    // Meetod avaldab varjatud ruudu(d)
    public void avaldaRuut(int rida, int veerg) {
        Ruudud ruut = väli[rida][veerg];
        if (!ruut.isNähtav()) {
            ruut.setNähtav();
        }

        // Kui tühi ruut ei puuduta ühtegi miini, siis avaldatakse ka tema naaberruudud
        if (ruut.getMitu() == 0) {
            for (int i = rida - 1; i < rida + 2 && i < väli.length; i++) {
                if (i < 0) i = 0;
                for (int j = veerg - 1; j < veerg + 2 && j < väli.length; j++) {
                    if (j < 0) j = 0;
                    if (i == rida && j == veerg) continue;
                    if (!väli[i][j].isNähtav()) avaldaRuut(i, j);
                }
            }
        }
    }
}