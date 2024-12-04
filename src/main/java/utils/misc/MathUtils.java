package utils.misc;

public class MathUtils {
    public static double interpolate(double from, double to, double pct) {
        return (from + (to - from) * pct);
    }

    public static float interpolateFloat(float from, float to, float pct) {
        return (float) interpolate(from, to, pct);
    }

    public static int interpolateInt(int from, int to, float pct) {
        return (int) interpolate(from, to, pct);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static boolean basicallyEqual(double value, double target, double tolerance) {
        return Math.abs(value - target) <= tolerance;
    }
}
