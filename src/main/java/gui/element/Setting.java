package gui.element;

import net.minecraft.client.gui.Gui;
import utils.Utils;

import java.awt.*;

public class Setting implements Utils {
    private int x, y, width, height;
    private SettingsType type;
    private String name, description;
    private boolean boolValue, draggingSlider;
    private double sliderValue, minValue, maxValue;

    public enum SettingsType {
        BOOLEAN,
        SLIDER
    }

    public Setting(String name, String description, boolean boolValue) {
        this.name = name;
        this.description = description;
        this.type = SettingsType.BOOLEAN;
        this.boolValue = boolValue;
        this.width = 100;
        this.height = 20;
    }

    public Setting(String name, String description, double sliderValue, double minValue, double maxValue) {
        this.name = name;
        this.description = description;
        this.type = SettingsType.SLIDER;
        this.sliderValue = sliderValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.width = 100;
        this.height = 20;
    }

    public void draw(int mouseX, int mouseY, Color color, float alpha) {
        Gui.drawRect(x, y + 10, x + width, y + height + 5, color.getRGB());

        switch (type) {
            case BOOLEAN:
                mc.fontRendererObj.drawString(name, x - 2, y + 5, Color.WHITE.getRGB());

                int BoolColor = boolValue ? Color.GREEN.getRGB() : Color.RED.getRGB();
                Gui.drawRect(x + width - 25, y + 5, x + width - 5, y + 15, BoolColor);

                if (isHovered(mouseX, mouseY)) {
                    mc.fontRendererObj.drawString(description, mouseX + 25, mouseY, Color.WHITE.getRGB());
                    return;
                }
                break;
            case SLIDER:
                mc.fontRendererObj.drawString(name, x - 2, y - 5, Color.WHITE.getRGB());
                String sliderValueStr = String.format("%.2f", this.sliderValue);
                mc.fontRendererObj.drawString(sliderValueStr, x + width - mc.fontRendererObj.getStringWidth(sliderValueStr) - 2, y + 2, Color.WHITE.getRGB());

                Gui.drawRect(x + 5, y + height - 10, x + width - 5, y + height - 8, new Color(0, 0, 0, 100).getRGB());
                double percentage = (this.sliderValue - minValue) / (maxValue - minValue);
                int sliderX = (int) (x + 5 + percentage * (width - 10));
                Gui.drawRect(sliderX - 2, y + height - 15, sliderX + 2, y + height - 5, Color.WHITE.getRGB());

                if (isHovered(mouseX, mouseY)) {
                    mc.fontRendererObj.drawString(description, mouseX + 25, mouseY, Color.WHITE.getRGB());
                    return;
                }
                break;
            default:
                break;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (type == type.BOOLEAN && mouseButton == 0) {
            boolValue = !boolValue;
        } else if (type == type.SLIDER) {
            if (isSliderHovered(mouseX, mouseY)) {
                draggingSlider = true;
                updateSliderValue(mouseX);
            }
        }
    }

    public void handleMouseDrag(int mouseX) {
        if (type == type.SLIDER && draggingSlider) {
            updateSliderValue(mouseX);
        }
    }

    private void updateSliderValue(int mouseX) {
        if (type == type.SLIDER) {
            double percentage = Math.max(0, Math.min(1, (mouseX - (x + 5)) / (double)(width - 10)));
            sliderValue = minValue + percentage * (maxValue - minValue);
            sliderValue = Math.round(sliderValue * 100.0) / 100.0;
        }
    }

    public boolean isSliderHovered(int mouseX, int mouseY) {
        return type == type.SLIDER &&
                mouseX >= x && mouseX <= x + width &&
                mouseY >= y + height - 15 && mouseY <= y + height + 5;
    }

    public void handleMouseRelease() {
        draggingSlider = false;
    }

    public boolean BoolValue() {
        return boolValue;
    }

    public double SliderValue() {
        return sliderValue;
    }

    public String getName() {
        return name;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getDimensions() {
        return new int[]{x, y, width, height};
    }
}