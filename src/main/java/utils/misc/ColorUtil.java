package utils.misc;

import java.awt.*;

public class ColorUtil {
    public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity) {
        if (speed == 0) {
            return new Color(255, 255, 255, 255);
        }
        int angle = (int) ((System.currentTimeMillis() * speed / 1000 + index) % 360);
        float hue = angle / 360f;
        Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
    }

    public static int interpolateColor(Color startColor, Color endColor, float progress) {
        progress = Math.max(0, Math.min(1, progress));

        int startRed = startColor.getRed();
        int startGreen = startColor.getGreen();
        int startBlue = startColor.getBlue();
        int startAlpha = startColor.getAlpha();

        int endRed = endColor.getRed();
        int endGreen = endColor.getGreen();
        int endBlue = endColor.getBlue();
        int endAlpha = endColor.getAlpha();

        int red = (int) (startRed + (endRed - startRed) * progress);
        int green = (int) (startGreen + (endGreen - startGreen) * progress);
        int blue = (int) (startBlue + (endBlue - startBlue) * progress);
        int alpha = (int) (startAlpha + (endAlpha - startAlpha) * progress);

        return new Color(red, green, blue, alpha).getRGB();
    }
}