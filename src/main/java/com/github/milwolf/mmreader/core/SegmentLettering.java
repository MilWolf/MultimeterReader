package com.github.milwolf.mmreader.core;

/**
 * @author MilWolf
 */
public class SegmentLettering {

    public static char getCharacter(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g) {

        if (a & b & c & d & e & !f & g) {
            return '0';
        } else if (!a & !b & !c & !d & e & !f & g) {
            return '1';
        } else if (a & !b & c & d & !e & f & g) {
            return '2';
        } else if (!a & !b & c & d & e & f & g) {
            return '3';
        } else if (!a & b & !c & !d & e & f & g) {
            return '4';
        } else if (!a & b & c & d & e & f & !g) {
            return '5';
        } else if (a & b & c & d & e & f & !g) {
            return '6';
        } else if ((!a & b & c & !d & e & !f & g) || (!a & !b & c & !d & e & !f & g)) {
            return '7';
        } else if (a & b & c & d & e & f & g) {
            return '8';
        } else if (!a & b & c & d & e & f & g) {
            return '9';
        }

        return 'N';
    }
}
