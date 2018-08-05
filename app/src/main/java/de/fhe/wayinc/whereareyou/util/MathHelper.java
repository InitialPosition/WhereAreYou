package de.fhe.wayinc.whereareyou.util;

import java.util.Random;

/**
 * This class helps with problems related to maths
 */

public class MathHelper {

    /**
     * Generate a random integer in a given range
     * @param min The lower end of the range
     * @param max The upper end of the range
     * @return A random integer
     */
    public static int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max + 1) - min) + min;
    }
}
