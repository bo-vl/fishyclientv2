package gui.element;

import gui.Config;
import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Category implements Utils {
    private static List<Category> categories = new ArrayList<>();

    private String name;
    private int x, y, width, height;
    private boolean expanded;
    private List<Module> modules = new ArrayList<>();

    public Category(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = 120;
        this.height = 25;
        this.expanded = Config.loadCategoryState(name);
        categories.add(this);
    }

    public void draw(int mouseX, int mouseY, Color color, float alpha) {
        Gui.drawRect(x, y, x + width, y + height, headerColor.getRGB());
        int expandedHeight = calculateExpandedHeight();

        if (expanded && !modules.isEmpty()) {
            Gui.drawRect(x, y + height, x + width, y + height + expandedHeight, expandedColor.getRGB());
        }

        if (isHovered(mouseX, mouseY)) {
            Gui.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 20).getRGB());
        }

        RenderUtil.RenderText(name, x + 10, y + 7, textColor);

        if (expanded) {
            drawModules(mouseX, mouseY, color, alpha);
        }
    }

    private int calculateExpandedHeight() {
        int moduleHeight = 0;
        for (Module module : modules) {
            moduleHeight += module.getDimensions()[3];

            if (module.SettingsExpanded()) {
                moduleHeight += module.getSettings().size() * 20;
            }
        }
        return moduleHeight;
    }

    private void drawModules(int mouseX, int mouseY, Color color, float alpha) {
        int currentY = y + height;

        for (Module module : modules) {
            module.draw(mouseX, mouseY, currentY, color, alpha);
            currentY += module.getDimensions()[3];

            if (module.SettingsExpanded()) {
                for (Setting setting : module.getSettings()) {
                    setting.setPosition(x, currentY);
                    setting.draw(mouseX, mouseY, null, 1f);
                    currentY += setting.getDimensions()[3];
                }
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public void toggleExpansion() {
        this.expanded = !this.expanded;
        Config.saveCategoryState(name, expanded);
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

    public static List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }
}