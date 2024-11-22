package gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Setting {
    private String name;
    private SettingType type;
    private int x, y, width, height;
    private boolean boolValue;
    private double sliderValue;
    private double minValue;
    private double maxValue;
    private boolean draggingSlider = false;
    private final int id;

    private static int idCounter = 0;

    public enum SettingType {
        BOOLEAN,
        SLIDER
    }

    public Setting(String name, boolean defaultValue) {
        this.name = name;
        this.type = SettingType.BOOLEAN;
        this.boolValue = defaultValue;
        this.width = 100;
        this.height = 20;
        this.id = idCounter++;
    }

    public Setting(String name, double minValue, double maxValue, double defaultValue) {
        this.name = name;
        this.type = SettingType.SLIDER;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.sliderValue = defaultValue;
        this.width = 100;
        this.height = 20;
        this.id = idCounter++;
    }

    public void draw(int mouseX, int mouseY) {
        int backgroundColor = 0x80000000;
        int textColor = 0xFFFFFFFF;
        Gui.drawRect(x, y, x + width, y + height, backgroundColor);

        if (type == SettingType.BOOLEAN) {
            Minecraft.getMinecraft().fontRendererObj.drawString(name, x + 5, y + 5, textColor);
            int boxColor = boolValue ? 0xFF00FF00 : 0xFFFF0000;
            Gui.drawRect(x + width - 25, y + 5, x + width - 5, y + 15, boxColor);
        } else if (type == SettingType.SLIDER) {
            Minecraft.getMinecraft().fontRendererObj.drawString(name, x + 5, y, textColor);

            String valueText = String.format("%.2f", sliderValue);
            Minecraft.getMinecraft().fontRendererObj.drawString(valueText, x + width - 30, y, textColor);

            Gui.drawRect(x + 5, y + height - 10, x + width - 5, y + height - 8, 0xFFAAAAAA);

            double percentage = (sliderValue - minValue) / (maxValue - minValue);
            int sliderX = x + 5 + (int)((width - 10) * percentage);

            Gui.drawRect(sliderX - 2, y + height - 15, sliderX + 2, y + height - 5, 0xFFFFFFFF);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public boolean isSliderHovered(int mouseX, int mouseY) {
        return type == SettingType.SLIDER &&
                mouseX >= x && mouseX <= x + width &&
                mouseY >= y + height - 15 && mouseY <= y + height + 5;
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (type == SettingType.BOOLEAN && mouseButton == 0) {
            boolValue = !boolValue;
        } else if (type == SettingType.SLIDER) {
            if (isSliderHovered(mouseX, mouseY)) {
                draggingSlider = true;
                updateSliderValue(mouseX);
            }
        }
    }

    public void handleMouseRelease() {
        draggingSlider = false;
    }

    public void handleMouseDrag(int mouseX) {
        if (type == SettingType.SLIDER && draggingSlider) {
            updateSliderValue(mouseX);
        }
    }

    private void updateSliderValue(int mouseX) {
        if (type == SettingType.SLIDER) {
            double percentage = Math.max(0, Math.min(1, (mouseX - (x + 5)) / (double)(width - 10)));
            sliderValue = minValue + percentage * (maxValue - minValue);
            sliderValue = Math.round(sliderValue * 100.0) / 100.0;
        }
    }

    public int getHeight() {
        return height;
    }
}
