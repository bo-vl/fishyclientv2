package utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import java.awt.*;

public interface Utils {
    Minecraft mc = Minecraft.getMinecraft();

    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldRenderer = tessellator.getWorldRenderer();

    public Color baseColor = new Color(40, 40, 40, 230);
    public Color hoverColor =  new Color(28, 28, 28, 230);;
    public Color enabledColor = new Color(0, 255, 0, 100);
    public Color disabledColor = new Color(255, 0, 0, 100);
    public Color textColor = new Color(255, 255, 255);
    public Color settingsIndicatorColor = new Color(150, 150, 250);
    public Color headerColor = new Color(25, 25, 25, 250);
    public Color expandedColor = new Color(50, 50, 50, 230);
    public Color accentColor = new Color(100, 100, 250);
}
