package utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import java.awt.*;

public interface Utils {
    Minecraft mc = Minecraft.getMinecraft();

    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();

    public Color baseColor = new Color(40, 40, 40, 230);
    public Color hoverColor =  new Color(28, 28, 28, 230);;
    public Color enabledColor = new Color(76, 175, 80, 200);
    public Color disabledColor = new Color(244, 67, 54, 100);
    public Color textColor = new Color(255, 255, 255);
    public Color headerColor = new Color(25, 25, 25, 250);
    public Color expandedColor = new Color(50, 50, 50, 230);
}
