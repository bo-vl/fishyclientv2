package gui;

import gui.element.Category;
import gui.element.Module;
import gui.element.Setting;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import utils.render.RenderUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.main.getClickGuiKey;

public class ClickGui extends GuiScreen {
    private List<Category> categoryList = new ArrayList<>();

    public ClickGui() {
        categoryList.add(new Category("Skyblock", 50, 50));
        categoryList.add(new Category("Render", 200, 50));
        categoryList.add(new Category("Combat", 350, 50));
        categoryList.add(new Category("Settings", 500, 50));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color color = Color.black;
        float alpha = 1.0f;
        drawCustomScreen(mouseX, mouseY, color, alpha);
    }

    public void drawCustomScreen(int mouseX, int mouseY, Color color, float alpha) {
        for (Category category : categoryList) {
            category.draw(mouseX, mouseY, color, alpha);

            if (category.isExpanded()) {
                List<Module> modules = Modules.getModulesByCategory(category.getName());
                int moduleY = category.getDimensions()[1] + 25;
                for (Module module : modules) {
                    module.setPosition(category.getDimensions()[0], moduleY);
                    module.draw(mouseX, mouseY, moduleY, color, alpha);
                    moduleY += module.getDimensions()[3] + 2;

                    if (module.SettingsExpanded()) {
                        int settingY = moduleY;
                        for (Setting setting : module.getSettings()) {
                            setting.setPosition(module.getDimensions()[0], settingY);
                            setting.draw(mouseX, mouseY, color, alpha);
                            settingY += setting.getDimensions()[3] + 2;
                        }
                        moduleY = settingY;
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Category category : categoryList) {
            if (RenderUtil.isHovered(mouseX, mouseY, category.getDimensions()[0], category.getDimensions()[1], category.getDimensions()[2], category.getDimensions()[3])) {
                category.toggleExpansion();
                break;
            }

            if (category.isExpanded()) {
                List<Module> modules = Modules.getModulesByCategory(category.getName());
                for (Module module : modules) {
                    if (RenderUtil.isHovered(mouseX, mouseY, module.getDimensions()[0], module.getDimensions()[1], module.getDimensions()[2], module.getDimensions()[3]) && mouseButton == 1) {
                        module.toggleSettings();
                        break;
                    }

                    if (RenderUtil.isHovered(mouseX, mouseY, module.getDimensions()[0], module.getDimensions()[1], module.getDimensions()[2], module.getDimensions()[3])) {
                        module.toggle();
                        break;
                    }

                    if (module.SettingsExpanded()) {
                        for (Setting setting : module.getSettings()) {
                            if (RenderUtil.isHovered(mouseX, mouseY, setting.getDimensions()[0], setting.getDimensions()[1], setting.getDimensions()[2], setting.getDimensions()[3])) {
                                setting.handleMouseClick(mouseX, mouseY, mouseButton);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        for (Category category : categoryList) {
            if (category.isExpanded()) {
                List<Module> modules = Modules.getModulesByCategory(category.getName());
                for (Module module : modules) {
                    if (module.SettingsExpanded()) {
                        for (Setting setting : module.getSettings()) {
                            setting.handleMouseDrag(mouseX);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Category category : categoryList) {
            if (category.isExpanded()) {
                List<Module> modules = Modules.getModulesByCategory(category.getName());
                for (Module module : modules) {
                    if (module.SettingsExpanded()) {
                        for (Setting setting : module.getSettings()) {
                            setting.handleMouseRelease();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == getClickGuiKey().getKeyCode()) {
            mc.displayGuiScreen(null);
            Config.save();
        }
    }
}