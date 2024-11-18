package gui;

import net.minecraft.client.gui.GuiScreen;
import gui.element.Category;
import gui.element.Module;
import gui.util.ModuleManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {
    private List<Category> categories = new ArrayList<>();

    public ClickGui() {
        Config.loadConfig();
        categories.add(new Category("Skyblock", 50, 50));
        categories.add(new Category("Helper", 200, 50));
        categories.add(new Category("Filler", 350, 50));
        categories.add(new Category("Filler1", 500, 50));
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
                for (int i = 0; i < modules.size(); i++) {
                    Module module = modules.get(i);
                    module.setPosition(category.getX(), baseY + (i * 25));
                    module.draw(mouseX, mouseY);
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
        }

        for (Module module : ModuleManager.getModules().values()) {
            if (module.isHovered(mouseX, mouseY)) {
                module.toggle();
                break;
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RSHIFT) {
            mc.displayGuiScreen(null);
        }
    }
}