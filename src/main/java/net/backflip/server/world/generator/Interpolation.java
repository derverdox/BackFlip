package net.backflip.server.world.generator;

public class Interpolation {

    public static double bilinearInterpolation(double[] array, int width, int realWidth, double x, double y) {
        if (realWidth % width != 0) {
            throw new RuntimeException("realWidth % width != 0");
        }
        if (x > realWidth || y > realWidth) {
            throw new RuntimeException("x > realWidth || y > realWidth");
        }
        int dist = realWidth / width;
        int sX = (int) (x / dist);
        int sY = (int) (y / dist);
        sX = sX == width - 1 ? sX - 1 : sX;
        sY = sY == width - 1 ? sY - 1 : sY;
        double[] subArray = new double[]{
                Array2d.get(array, width, sX, sY),
                Array2d.get(array, width, sX + 1, sY),
                Array2d.get(array, width, sX, sY + 1),
                Array2d.get(array, width, sX + 1, sY + 1)
        };
        double sDX = x / dist - sX;
        double sDY = y / dist - sY;

        return (1 - sDY) * ((1 - sDX) * subArray[0] + (sDX) * subArray[1]) +
                (sDY) * ((1 - sDX) * subArray[2] + (sDX) * subArray[3]);
    }
}
