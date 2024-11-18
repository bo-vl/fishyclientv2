package main;

import gui.ClickGui;
import gui.util.ModuleInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ClickGuiHandler {
    private static final KeyBinding CLICK_GUI_KEY = new KeyBinding(
            "key.clickgui.desc",
            Keyboard.KEY_RSHIFT,
            "key.categories.misc"
    );

    public static void init() {
        ClientRegistry.registerKeyBinding(CLICK_GUI_KEY);
        ModuleInitializer.init();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (CLICK_GUI_KEY.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ClickGui());
        }
    }
}