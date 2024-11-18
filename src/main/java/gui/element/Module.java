package gui.element;

import gui.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Module {
    private String name;
    private String category;
    protected boolean enabled;
    private int x, y, width, height;

    public Module(String name, String category) {
        this.name = name;
        this.category = category;
        this.width = 100;
        this.height = 20;
        this.enabled = Config.loadModuleState(name);
    }

    public void draw(int mouseX, int mouseY) {
        int backgroundColor = 0x80000000;
        int textColor = 0xFFFFFFFF;
        int outlineColor = enabled ? 0xFF00FF00 : 0xFFFF0000;

        Gui.drawRect(x, y, x + width, y + height, backgroundColor);
        Minecraft.getMinecraft().fontRendererObj.drawString(name, x + 5, y + 5, textColor);
        Gui.drawRect(x - 1, y - 1, x + width + 1, y, outlineColor);
        Gui.drawRect(x - 1, y + height, x + width + 1, y + height + 1, outlineColor);
        Gui.drawRect(x - 1, y, x, y + height, outlineColor);
        Gui.drawRect(x + width, y, x + width + 1, y + height, outlineColor);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void toggle() {
        enabled = !enabled;
        Config.saveModuleState(name, enabled);
    }

    public boolean isEnabled() {
        return enabled;
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
}