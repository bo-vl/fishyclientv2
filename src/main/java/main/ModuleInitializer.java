package main;

import gui.Modules;
import modules.Render.*;
import modules.Settings.*;
import modules.Skyblock.*;
import modules.Debug.*;

public class ModuleInitializer {
    public static void init() {
        Modules.registerModule(new Pelt());
        Modules.registerModule(new Pest());
        Modules.registerModule(new Mineshaft());
        Modules.registerModule(new Endernode());
        Modules.registerModule(new Dojo());
        Modules.registerModule(new HUD());
        Modules.registerModule(new ShowArmorStands());
    }
}
