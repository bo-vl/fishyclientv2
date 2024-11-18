package gui.element;

import gui.Config;
import gui.util.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Category {
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

    public void draw(int mouseX, int mouseY) {
        int backgroundColor = 0x80000000;
        int textColor = 0xFFFFFFFF;

        Gui.drawRect(x, y, x + width, y + height, backgroundColor);
        Minecraft.getMinecraft().fontRendererObj.drawString(name, x + 5, y + 5, textColor);

        if (expanded) {
            int baseY = y + height + 5;
            for (int i = 0; i < ModuleManager.getModulesByCategory(name).size(); i++) {
                Module module = ModuleManager.getModulesByCategory(name).get(i);
                module.setPosition(x, baseY + (i * 25));
                module.draw(mouseX, mouseY);
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void toggleExpand() {
        expanded = !expanded;
        Config.saveCategoryState(name, expanded);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}