package gui.element;

import gui.Config;
import net.minecraft.client.gui.Gui;
import utils.Utils;
import utils.misc.ColorUtil;
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
        int backgroundColor = isHovered(mouseX, mouseY) ? ColorUtil.interpolateColor(baseColor, hoverColor, 0.2f) : baseColor.getRGB();
        Gui.drawRect(x, y, x + width, y + height, backgroundColor);

        Color stateColor;
        switch (type) {
            case BOOLEAN:
                stateColor = boolValue ? enabledColor : disabledColor;
                break;
            case SLIDER:
                stateColor = new Color(33, 150, 243, 200);
                break;
            default:
                stateColor = Color.GRAY;
        }
        Gui.drawRect(x, y, x + 4, y + height, stateColor.getRGB());

        RenderUtil.RenderText(name, x + 10, y + 5, textColor);

        switch (type) {
            case BOOLEAN:
                drawToggleSwitch();
                break;
            case SLIDER:
                drawSlider(mouseX, mouseY);
                break;
        }

        if (isHovered(mouseX, mouseY)) {
            RenderUtil.RenderText(description, mouseX + 10, mouseY, new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 180));
        }
    }

    private void drawToggleSwitch() {
        int toggleWidth = 30;
        int toggleHeight = 12;
        int toggleX = x + width - toggleWidth - 10;
        int toggleY = y + (height - toggleHeight) / 2;

        Color switchBg = boolValue ? enabledColor : disabledColor;
        Gui.drawRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, switchBg.getRGB());

        int handleX = boolValue ? toggleX + toggleWidth - toggleHeight + 2 : toggleX + 2;
        Gui.drawRect(handleX, toggleY + 2, handleX + toggleHeight - 4, toggleY + toggleHeight - 2, Color.WHITE.getRGB());
    }

    private void drawSlider(int mouseX, int mouseY) {
        String sliderValueStr = String.format("%.2f", this.sliderValue);
        int valueWidth = mc.fontRendererObj.getStringWidth(sliderValueStr);
        RenderUtil.RenderText(sliderValueStr, x + width - valueWidth - 10, y + 5, new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 180));

        int trackY = y + height - 5;
        Gui.drawRect(x + 10, trackY, x + width - 10, trackY + 2, new Color(158, 158, 158, 100).getRGB());

        double percentage = (this.sliderValue - minValue) / (maxValue - minValue);
        int sliderHandleX = (int) (x + 10 + percentage * (width - 20));
        Gui.drawRect(sliderHandleX - 3, y + height - 8, sliderHandleX + 3, y + height - 2, new Color(33, 150, 243, 200).getRGB());
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

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        if (type == SettingsType.BOOLEAN) {
            return boolValue;
        } else if (type == SettingsType.SLIDER) {
            return sliderValue;
        }
        return null;
    }
}