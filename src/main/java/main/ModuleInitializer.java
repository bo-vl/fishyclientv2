package main;

import gui.Modules;
import modules.Render.PeltHelper;
import modules.Render.PestHelper;

public class ModuleInitializer {
    public static void init() {
        Modules.registerModule(new PeltHelper());
        Modules.registerModule(new PestHelper());
    }
}
