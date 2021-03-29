package com.github.milwolf.mmreader.core.fs9721lp3;

/**
 * @author MilWolf
 */
public class Record {

    double value;
    Unit unit;
    Metric metric;
    Temp temp = new Temp();

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    public Metric getMetric() {
        return metric;
    }

    @Override
    public String toString() {
        return "Record{" +
                "value='" + value + '\'' +
                ", unit=" + unit +
                ", metric=" + metric +
                '}';
    }

    public enum Unit {

        FARADS("F"),
        OHMS("Ω"),
        AMPERE("A"),
        VOLT("V"),
        HERTZ("Hz");

        private final String abbreviation;

        Unit(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }

    public enum Metric {

        NANO("n"),
        MILLI("m"),
        UNIT(""),
        KILO("k"),
        MEGA("M"),
        PERCENT("%");

        private final String abbreviation;

        Metric(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }

    public class Temp {

        boolean A1, B1, C1, D1, E1, F1, G1;
        boolean A2, B2, C2, D2, E2, F2, G2;
        boolean A3, B3, C3, D3, E3, F3, G3;
        boolean A4, B4, C4, D4, E4, F4, G4;
        boolean DP1, DP2, DP3;

        boolean AC;
        boolean DC;
        boolean auto;
        boolean RS232;

        boolean negative;

        boolean u, n, k, diode, milli, percent, mega, beep;
        boolean farads, ohms, rel, hold;
        boolean A, V, Hz, lowBattery;
    }
}
