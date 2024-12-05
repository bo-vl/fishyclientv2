package main;

import gui.Modules;
import modules.Render.Mineshaft;
import modules.Render.Endernode;
import modules.Render.Pelt;
import modules.Render.Pest;
import modules.Settings.HUD;
import modules.Skyblock.Dojo;

public class ModuleInitializer {
    public static void init() {
        Modules.registerModule(new Pelt());
        Modules.registerModule(new Pest());
        Modules.registerModule(new Mineshaft());
        Modules.registerModule(new Endernode());
        Modules.registerModule(new Dojo());
        Modules.registerModule(new HUD());
    }
}
