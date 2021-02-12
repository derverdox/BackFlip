package net.backflip.server.world.generator;

public class Array2d {

    public static int get(int[] array, int width, int x, int y) {
        return array[y * width + x];
    }

    public static float get(float[] array, int width, int x, int y) {
        return array[y * width + x];
    }

    public static double get(double[] array, int width, int x, int y) {
        return array[y * width + x];
    }

    public static void set(int[] array, int width, int x, int y, int value) {
        array[y * width + x] = value;
    }

    public static void set(float[] array, int width, int x, int y, float value) {
        array[y * width + x] = value;
    }

    public static void set(double[] array, int width, int x, int y, double value) {
        array[y * width + x] = value;
    }

    public static int width(int[] array) {
        return (int) Math.sqrt(array.length);
    }

    public static int width(float[] array) {
        return (int) Math.sqrt(array.length);
    }

    public static int width(double[] array) {
        return (int) Math.sqrt(array.length);
    }
}
