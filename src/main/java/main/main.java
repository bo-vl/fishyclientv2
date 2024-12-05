package main;

import gui.ClickGui;
import gui.Config;
import modules.Settings.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import proxy.CommonProxy;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class main {
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    private static final KeyBinding CLICK_GUI_KEY = new KeyBinding(
            "Fishy client Gui",
            Keyboard.KEY_RSHIFT,
            "Fishy client v2"
    );

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle("FishyClientV2");
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.init(event);
        ClientRegistry.registerKeyBinding(CLICK_GUI_KEY);
        MinecraftForge.EVENT_BUS.register(new HUD());
        MinecraftForge.EVENT_BUS.register(this);
        ModuleInitializer.init();
        Config.load();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (CLICK_GUI_KEY.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ClickGui());
        }
    }

    public static KeyBinding getClickGuiKey() {
        return CLICK_GUI_KEY;
    }
}