package gui.util;

import modules.Render.PeltHelper;
import net.minecraftforge.common.MinecraftForge;

public class ModuleInitializer {
    public static void init() {
        ModuleManager.registerModule(new PeltHelper());
    }
}