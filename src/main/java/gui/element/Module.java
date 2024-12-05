package gui.element;

import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Module implements Utils {
    private String name, category;
    protected boolean enabled;
    private boolean settingsExpanded = false;
    private int x, y, width, height;
    private List<Setting> settings = new ArrayList<>();

    public Module(String name, String category) {
        this.name = name;
        this.category = category;
        this.width = 120;
        this.height = 20;
    }

    public void draw(int mouseX, int mouseY, int offsetY, Color color, float alpha) {
        this.y = offsetY;

        Gui.drawRect(x, y, x + width, y + height, baseColor.getRGB());

        if (isHovered(mouseX, mouseY)) {
            Gui.drawRect(x, y, x + width, y + height, hoverColor.getRGB());
        }

        Color stateColor = enabled ? enabledColor : disabledColor;
        Gui.drawRect(x, y, x + 5, y + height, stateColor.getRGB());

        RenderUtil.RenderText(name, x + 10, y + 6, textColor);

        int outlineColor = enabled ? enabledColor.getRGB() : disabledColor.getRGB();
        Gui.drawRect(x - 1, y - 1, x + width + 1, y, outlineColor);
        Gui.drawRect(x - 1, y + height, x + width + 1, y + height + 1, outlineColor);
        Gui.drawRect(x - 1, y, x, y + height, outlineColor);
        Gui.drawRect(x + width, y, x + width + 1, y + height, outlineColor);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void toggleSettings() {
        this.settingsExpanded = !this.settingsExpanded;
    }

    public int[] getDimensions() {
        return new int[]{x, y, width, height};
    }

    public boolean SettingsExpanded() {
        return settingsExpanded;
    }

    public List<gui.element.Setting> getSettings() {
        return settings;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
    }
}