package gui.element;

import gui.Config;
import net.minecraft.client.gui.Gui;
import utils.Utils;

import java.awt.*;
import java.util.*;
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
        this.width = 100;
        this.height = 20;
        this.enabled = Config.loadModuleState(name);
    }

    public void draw(int mouseX, int MouseY, int offsetY, Color color, float alpha) {
        this.y = offsetY + 1;

        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
        mc.fontRendererObj.drawString(name, x + 5, y + 5, Color.WHITE.getRGB());
        int outlineColor = enabled ? Color.GREEN.getRGB() : Color.RED.getRGB();

        Gui.drawRect(x - 1, y - 1, x + width + 1, y, outlineColor);
        Gui.drawRect(x - 1, y + height, x + width + 1, y + height + 1, outlineColor);
        Gui.drawRect(x - 1, y, x, y + height, outlineColor);
        Gui.drawRect(x + width, y, x + width + 1, y + height, outlineColor);
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
        Config.saveModuleState(name, enabled);
        enabled = !enabled;
    }
}
