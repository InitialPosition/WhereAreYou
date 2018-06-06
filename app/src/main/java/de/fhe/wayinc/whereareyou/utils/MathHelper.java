package de.fhe.wayinc.whereareyou.utils;

import java.util.Random;

public class MathHelper {
    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max + 1) - min) + min;
    }
}
