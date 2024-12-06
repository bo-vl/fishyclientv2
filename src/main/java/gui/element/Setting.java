package gui.element;

import gui.Config;
import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.render.RenderUtil;

import java.awt.*;

public class Setting implements Utils {
    private int x, y, width, height;
    private SettingsType type;
    private String name, description;
    private boolean boolValue, draggingSlider, moduleExpanded;
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
        this.width = 120;
        this.height = 20;
    }

    public Setting(String name, String description, double sliderValue, double minValue, double maxValue) {
        this.name = name;
        this.description = description;
        this.type = SettingsType.SLIDER;
        this.sliderValue = sliderValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.width = 120;
        this.height = 20;
    }

    public void draw(int mouseX, int mouseY, Color color, float alpha) {
        if (isHovered(mouseX, mouseY)) {
            Gui.drawRect(x, y, x + width, y + height, hoverColor.getRGB());
        } else {
            Gui.drawRect(x, y, x + width, y + height, baseColor.getRGB());
        }

        Color stateColor;
        switch (type) {
            case BOOLEAN:
                stateColor = boolValue ? enabledColor : disabledColor;
                break;
            case SLIDER:
                stateColor = settingsIndicatorColor;
                break;
            default:
                stateColor = Color.GRAY;
        }
        Gui.drawRect(x, y, x + 5, y + height, stateColor.getRGB());

        RenderUtil.RenderText(name, x + 10, y + 5, textColor);

        switch (type) {
            case BOOLEAN:
                int toggleWidth = 20;
                int toggleHeight = 10;
                int toggleX = x + width - toggleWidth - 5;
                int toggleY = y + (height - toggleHeight) / 2;

                int boolColor = boolValue ? enabledColor.getRGB() : disabledColor.getRGB();
                Gui.drawRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, boolColor);
                break;

            case SLIDER:
                String sliderValueStr = String.format("%.2f", this.sliderValue);
                int valueWidth = mc.fontRendererObj.getStringWidth(sliderValueStr);
                RenderUtil.RenderText(sliderValueStr, x + width - valueWidth - 5, y + 3, textColor);

                int trackY = y + height - 5;
                Gui.drawRect(x + 10, trackY, x + width - 10, trackY + 2, new Color(50, 50, 50, 100).getRGB());

                double percentage = (this.sliderValue - minValue) / (maxValue - minValue);
                int sliderHandleX = (int) (x + 10 + percentage * (width - 20));
                Gui.drawRect(sliderHandleX - 2, y + height - 8, sliderHandleX + 2, y + height - 2, settingsIndicatorColor.getRGB());
                break;
        }

        int outlineColor = stateColor.getRGB();
        Gui.drawRect(x - 1, y - 1, x + width + 1, y, outlineColor);
        Gui.drawRect(x - 1, y + height, x + width + 1, y + height + 1, outlineColor);
        Gui.drawRect(x - 1, y, x, y + height, outlineColor);
        Gui.drawRect(x + width, y, x + width + 1, y + height, outlineColor);

        if (isHovered(mouseX, mouseY)) {
            RenderUtil.RenderText(description, mouseX + 10, mouseY, textColor);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (type == SettingsType.BOOLEAN && mouseButton == 0) {
            boolValue = !boolValue;
            Config.saveToggleValue(name, boolValue);
        }
        if (type == SettingsType.SLIDER) {
            if (isSliderHovered(mouseX, mouseY)) {
                draggingSlider = true;
                updateSliderValue(mouseX);
                Config.saveSliderValue(name, (int) sliderValue);
            }
        }
    }

    public void handleMouseDrag(int mouseX) {
        if (type == SettingsType.SLIDER && draggingSlider) {
            updateSliderValue(mouseX);
        }
    }

    private void updateSliderValue(int mouseX) {
        if (type == SettingsType.SLIDER) {
            double percentage = Math.max(0, Math.min(1, (mouseX - (x + 5)) / (double)(width - 10)));
            sliderValue = Math.round(minValue + percentage * (maxValue - minValue));
        }
    }

    public boolean isSliderHovered(int mouseX, int mouseY) {
        return type == SettingsType.SLIDER &&
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

    public void setBoolValue(boolean value) {
        this.boolValue = value;
    }

    public void setSliderValue(double value) {
        this.sliderValue = value;
    }

    public SettingsType getType() {
        return type;
    }
}