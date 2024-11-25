package gui;

import gui.element.Setting;
import net.minecraft.client.gui.GuiScreen;
import gui.element.Category;
import gui.element.Module;
import gui.util.ModuleManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.main.getClickGuiKey;

public class ClickGui extends GuiScreen {
    private List<Category> categories = new ArrayList<>();

    public ClickGui() {
        Config.loadConfig();
        categories.add(new Category("Skyblock", 50, 50));
        categories.add(new Category("Render", 200, 50));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        for (Category category : categories) {
            category.draw(mouseX, mouseY);
        }

        for (Category category : categories) {
            if (category.isExpanded()) {
                List<Module> modules = ModuleManager.getModulesByCategory(category.getName());
                int baseY = category.getY() + 25;

                for (Module module : modules) {
                    module.setPosition(category.getX(), baseY);
                    module.draw(mouseX, mouseY, baseY);

                    baseY += module.getHeight();

                    if (module.isSettingsExpanded()) {
                        int settingsOffsetY = baseY;
                        for (Setting setting : module.getSettings()) {
                            setting.setPosition(module.getX(), settingsOffsetY);
                            setting.draw(mouseX, mouseY);
                            settingsOffsetY += setting.getHeight();
                        }
                        baseY = settingsOffsetY;
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Category category : categories) {
            if (category.isHovered(mouseX, mouseY)) {
                category.toggleExpand();
                break;
            }

            if (category.isExpanded()) {
                for (Module module : ModuleManager.getModulesByCategory(category.getName())) {
                    if (module.isHovered(mouseX, mouseY) && mouseButton == 1) {
                        module.toggleSettings();
                        break;
                    }

                    if (module.isSettingsExpanded()) {
                        for (Setting setting : module.getSettings()) {
                            if (setting.isHovered(mouseX, mouseY)) {
                                setting.handleMouseClick(mouseX, mouseY, mouseButton);
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (Module module : ModuleManager.getModules().values()) {
            if (module.isHovered(mouseX, mouseY) && mouseButton == 0) {
                module.toggle();
                break;
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        for (Category category : categories) {
            if (category.isExpanded()) {
                for (Module module : ModuleManager.getModulesByCategory(category.getName())) {
                    if (module.isSettingsExpanded()) {
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
        for (Category category : categories) {
            if (category.isExpanded()) {
                for (Module module : ModuleManager.getModulesByCategory(category.getName())) {
                    if (module.isSettingsExpanded()) {
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
        }
    }
}