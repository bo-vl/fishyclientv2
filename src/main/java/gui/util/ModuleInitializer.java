package gui.util;

import gui.element.Module;
import gui.element.Setting;
import modules.pelt.AutoWarpBack;

public class ModuleInitializer {
    public static void init() {
        AutoWarpBack autoWarpBack = new AutoWarpBack();

        autoWarpBack.addSetting(new Setting("Enabled", true));
        autoWarpBack.addSetting(new Setting("Range", 1.0, 10.0, 5.0));

        ModuleManager.registerModule(autoWarpBack);

        Module module = new Module("ExampleModule", "Helper");

        module.addSetting(new Setting("Enabled", true));
        module.addSetting(new Setting("Speed", 1.0, 10.0, 5.0));

        ModuleManager.registerModule(module);

        Module module1 = new Module("ExampleModule123", "Helper");

        module1.addSetting(new Setting("Enabled", true));
        module1.addSetting(new Setting("Speed", 1.0, 10.0, 5.0));

        ModuleManager.registerModule(module1);

        Module module2 = new Module("feinious module", "Helper");

        module2.addSetting(new Setting("Enabled", true));
        module2.addSetting(new Setting("Speed", 1.0, 10.0, 5.0));

        ModuleManager.registerModule(module2);
    }
}

