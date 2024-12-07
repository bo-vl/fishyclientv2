package gui.element;

import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.misc.ColorUtil;
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

        int backgroundColor = isHovered(mouseX, mouseY) ? ColorUtil.interpolateColor(baseColor, hoverColor, 0.2f) : baseColor.getRGB();
        Gui.drawRect(x, y, x + width, y + height, backgroundColor);

        Color stateColor = enabled ? enabledColor : disabledColor;
        Gui.drawRect(x, y, x + 4, y + height, stateColor.getRGB());

        RenderUtil.RenderText(name, x + 10, y + 6, textColor);

        if (settings != null && !settings.isEmpty()) {
            Color expandSymbolColor = new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 180);
            String symbol = settingsExpanded ? "-" : "+";
            RenderUtil.RenderText(symbol, x + width - 20, y + 6, expandSymbolColor);
        }
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

    public List<Setting> getSettings() {
        return settings;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
    }
}