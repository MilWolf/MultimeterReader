package com.github.milwolf.mmreader.core.fs9721lp3;

import com.github.milwolf.mmreader.core.SegmentLettering;

import java.io.InputStream;

/**
 * @author MilWolf
 * Decrypt semiconductor FS9721_LP3 format.
 * Reference = @see <a href="https://sigrok.org/wiki/Multimeter_ICs#Fortune_Semiconductor_FS9721_LP3">https://sigrok.org/wiki/Multimeter_ICs#Fortune_Semiconductor_FS9721_LP3</a>
 */
public class FS9721LP3Iterable implements IterableWithException<Record> {

    private final InputStream inputStream;
    private Record currentRecord = null;

    public FS9721LP3Iterable(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private static byte getBit(int position, int b) {
        return (byte) ((b >> position) & 1);
    }

    @Override
    public IteratorWithException<Record> iterator() {

        return new IteratorWithException<Record>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Record next() throws Exception {

                if (inputStream.available() > 0) {

                    int read = inputStream.read();
                    return processData(read);
                } else {
                    return null;
                }
            }
        };
    }

    private Record processData(int read) {

        byte bit0 = getBit(0, read);
        byte bit1 = getBit(1, read);
        byte bit2 = getBit(2, read);
        byte bit3 = getBit(3, read);

        byte bit4 = getBit(4, read);
        byte bit5 = getBit(5, read);
        byte bit6 = getBit(6, read);
        byte bit7 = getBit(7, read);

        int byteIndex = bit4 | bit5 << 1 | bit6 << 2 | bit7 << 3;

        if (byteIndex == 1) { //new record
            currentRecord = new Record();

            currentRecord.temp.RS232 = bit0 == 1;
            currentRecord.temp.auto = bit1 == 1;
            currentRecord.temp.DC = bit2 == 1;
            currentRecord.temp.AC = bit3 == 1;

        } else {
            if (currentRecord != null) {

                switch (byteIndex) {
                    case 2:
                        currentRecord.temp.negative = bit3 == 1;
                        currentRecord.temp.A1 = bit2 == 1;
                        currentRecord.temp.B1 = bit1 == 1;
                        currentRecord.temp.C1 = bit0 == 1;
                        break;
                    case 3:
                        currentRecord.temp.D1 = bit3 == 1;
                        currentRecord.temp.E1 = bit2 == 1;
                        currentRecord.temp.F1 = bit1 == 1;
                        currentRecord.temp.G1 = bit0 == 1;
                        break;
                    case 4:
                        currentRecord.temp.DP1 = bit3 == 1;
                        currentRecord.temp.A2 = bit2 == 1;
                        currentRecord.temp.B2 = bit1 == 1;
                        currentRecord.temp.C2 = bit0 == 1;
                        break;
                    case 5:
                        currentRecord.temp.D2 = bit3 == 1;
                        currentRecord.temp.E2 = bit2 == 1;
                        currentRecord.temp.F2 = bit1 == 1;
                        currentRecord.temp.G2 = bit0 == 1;
                        break;
                    case 6:
                        currentRecord.temp.DP2 = bit3 == 1;
                        currentRecord.temp.A3 = bit2 == 1;
                        currentRecord.temp.B3 = bit1 == 1;
                        currentRecord.temp.C3 = bit0 == 1;
                        break;
                    case 7:
                        currentRecord.temp.D3 = bit3 == 1;
                        currentRecord.temp.E3 = bit2 == 1;
                        currentRecord.temp.F3 = bit1 == 1;
                        currentRecord.temp.G3 = bit0 == 1;
                        break;
                    case 8:
                        currentRecord.temp.DP3 = bit3 == 1;
                        currentRecord.temp.A4 = bit2 == 1;
                        currentRecord.temp.B4 = bit1 == 1;
                        currentRecord.temp.C4 = bit0 == 1;
                        break;
                    case 9:
                        currentRecord.temp.D4 = bit3 == 1;
                        currentRecord.temp.E4 = bit2 == 1;
                        currentRecord.temp.F4 = bit1 == 1;
                        currentRecord.temp.G4 = bit0 == 1;

                        char char1 = SegmentLettering.getCharacter(currentRecord.temp.A1, currentRecord.temp.B1, currentRecord.temp.C1, currentRecord.temp.D1, currentRecord.temp.E1, currentRecord.temp.F1, currentRecord.temp.G1);
                        char char2 = SegmentLettering.getCharacter(currentRecord.temp.A2, currentRecord.temp.B2, currentRecord.temp.C2, currentRecord.temp.D2, currentRecord.temp.E2, currentRecord.temp.F2, currentRecord.temp.G2);
                        char char3 = SegmentLettering.getCharacter(currentRecord.temp.A3, currentRecord.temp.B3, currentRecord.temp.C3, currentRecord.temp.D3, currentRecord.temp.E3, currentRecord.temp.F3, currentRecord.temp.G3);
                        char char4 = SegmentLettering.getCharacter(currentRecord.temp.A4, currentRecord.temp.B4, currentRecord.temp.C4, currentRecord.temp.D4, currentRecord.temp.E4, currentRecord.temp.F4, currentRecord.temp.G4);

                        StringBuilder stringBuilder = new StringBuilder();

                        if (currentRecord.temp.negative) {
                            stringBuilder.append('-');
                        }

                        stringBuilder.append(char1);

                        if (currentRecord.temp.DP1) {
                            stringBuilder.append('.');
                        }

                        stringBuilder.append(char2);

                        if (currentRecord.temp.DP2) {
                            stringBuilder.append('.');
                        }

                        stringBuilder.append(char2);

                        if (currentRecord.temp.DP3) {
                            stringBuilder.append('.');
                        }

                        stringBuilder.append(char3);
                        stringBuilder.append(char4);

                        currentRecord.value = Double.parseDouble(stringBuilder.toString());

                        break;
                    case 10:
                        currentRecord.temp.u = bit3 == 1;
                        currentRecord.temp.n = bit2 == 1;
                        currentRecord.temp.k = bit1 == 1;
                        currentRecord.temp.diode = bit0 == 1;
                        break;
                    case 11:
                        currentRecord.temp.milli = bit3 == 1;
                        currentRecord.temp.percent = bit2 == 1;
                        currentRecord.temp.mega = bit1 == 1;
                        currentRecord.temp.beep = bit0 == 1;
                        break;
                    case 12:
                        currentRecord.temp.farads = bit3 == 1;
                        currentRecord.temp.ohms = bit2 == 1;
                        currentRecord.temp.rel = bit1 == 1;
                        currentRecord.temp.hold = bit0 == 1;
                        break;
                    case 13:
                        currentRecord.temp.A = bit3 == 1;
                        currentRecord.temp.V = bit2 == 1;
                        currentRecord.temp.Hz = bit1 == 1;
                        currentRecord.temp.lowBattery = bit0 == 1;
                        break;
                    case 14: //last byte of the record, user bits

                        if (currentRecord.temp.A) {
                            currentRecord.unit = Record.Unit.AMPERE;
                        } else if (currentRecord.temp.V) {
                            currentRecord.unit = Record.Unit.VOLT;
                        } else if (currentRecord.temp.Hz) {
                            currentRecord.unit = Record.Unit.HERTZ;
                        } else if (currentRecord.temp.farads) {
                            currentRecord.unit = Record.Unit.FARADS;
                        } else if (currentRecord.temp.ohms) {
                            currentRecord.unit = Record.Unit.OHMS;
                        }

                        if (currentRecord.temp.n) {
                            currentRecord.metric = Record.Metric.NANO;
                        } else if (currentRecord.temp.k) {
                            currentRecord.metric = Record.Metric.KILO;
                        } else if (currentRecord.temp.milli) {
                            currentRecord.metric = Record.Metric.MILLI;
                        } else if (currentRecord.temp.mega) {
                            currentRecord.metric = Record.Metric.MEGA;
                        } else if (currentRecord.temp.percent) {
                            currentRecord.metric = Record.Metric.PERCENT;
                        } else {
                            currentRecord.metric = Record.Metric.UNIT;
                        }

                        return currentRecord;
                }
            }
        }

        return null;
    }
}
