package gui.element;

import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.render.RenderUtil;

import java.awt.*;

public class Setting implements Utils {
    private int x, y, width, height;
    private SettingsType type;
    private String name, description;
    private boolean boolValue, draggingSlider, isColorPickerExpanded;
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
        Gui.drawRect(x, y, x + width, y + height + 10, color.getRGB());

        switch (type) {
            case BOOLEAN:
                int textY = y + (height - mc.fontRendererObj.FONT_HEIGHT) / 2;
                RenderUtil.RenderText(name, x + 5, textY, Color.WHITE);

                int toggleWidth = 20;
                int toggleHeight = 10;
                int toggleX = x + width - toggleWidth - 5;
                int toggleY = y + (height - toggleHeight) / 2;

                int boolColor = boolValue ? Color.GREEN.getRGB() : Color.RED.getRGB();
                Gui.drawRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, boolColor);

                if (isHovered(mouseX, mouseY)) {
                    RenderUtil.RenderText(description, mouseX + 10, mouseY, Color.WHITE);
                }
                break;

            case SLIDER:
                RenderUtil.RenderText(name, x + 2, y + 2, Color.WHITE);

                String sliderValueStr = String.format("%.2f", this.sliderValue);
                int valueWidth = mc.fontRendererObj.getStringWidth(sliderValueStr);
                RenderUtil.RenderText(sliderValueStr, x + width - valueWidth - 5, y + 2, Color.WHITE);

                Gui.drawRect(x + 5, y + height - 5, x + width - 5, y + height - 3, new Color(0, 0, 0, 100).getRGB());

                double percentage = (this.sliderValue - minValue) / (maxValue - minValue);
                int sliderX = (int) (x + 5 + percentage * (width - 10));
                Gui.drawRect(sliderX - 2, y + height - 10, sliderX + 2, y + height, Color.WHITE.getRGB());

                if (isHovered(mouseX, mouseY)) {
                    RenderUtil.RenderText(description, mouseX + 10, mouseY, Color.WHITE);
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
        }
        if (type == type.SLIDER) {
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
            sliderValue = Math.round(minValue + percentage * (maxValue - minValue));
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