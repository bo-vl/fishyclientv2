package gui.element;

import gui.Config;
import gui.Modules;
import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.render.RenderUtil;

import java.awt.*;

public class Category implements Utils {
    private String name;
    private int x, y, width, height;
    private boolean expanded;

    public Category(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 20;
        this.expanded = Config.loadCategoryState(name);
    }

    public void draw(int mouseX, int mouseY, Color color, float alpha) {
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
        RenderUtil.RenderText(name, x + 5, y + 5, Color.white);
    }

    public String getName() {
        return name;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public int[] getDimensions() {
        return new int[]{x, y, width, height};
    }

    public void toggleExpansion() {
        Config.saveCategoryState(name, expanded);
        this.expanded = !this.expanded;
    }
}
