package gui.util;

import modules.pelt.AutoWarpBack;

public class ModuleInitializer {
    public static void init() {
        ModuleManager.registerModule(new AutoWarpBack());
    }
}