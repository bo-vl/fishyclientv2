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
}