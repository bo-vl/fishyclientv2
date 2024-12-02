package gui;

import gui.element.Module;
import gui.element.Setting;

import net.minecraftforge.common.MinecraftForge;

import java.util.*;
import java.util.stream.Collectors;

public class Modules extends Module {
    private boolean registered = false;
    private static Map<String, Module> modules = new HashMap<>();
    private static final Map<String, Map<String, Setting>> moduleSettings = new HashMap<>();

    public Modules(String name, String category) {
        super(name, category);
    }

    public static void registerModule(Module module) {
        modules.put(module.getName(), module);
    }

    public static List<Module> getModulesByCategory(String category) {
        return modules.values().stream()
                .filter(module -> module.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public static void registerSetting(Module module, Setting setting) {
        moduleSettings.computeIfAbsent(module.getName(), k -> new HashMap<>())
                .put(setting.getName(), setting);
        module.addSetting(setting);
    }

    public void setActive(boolean active) {
        this.enabled = active;

        if (active && !registered) {
            MinecraftForge.EVENT_BUS.register(this);
            registered = true;
        } else if (!active && registered) {
            MinecraftForge.EVENT_BUS.unregister(this);
            registered = false;
        }
    }

    @Override
    public void toggle() {
        super.toggle();
        setActive(isEnabled());
    }

    public static Map<String, Module> getModules() {
        return modules;
    }

    private static Setting getSetting(String moduleName, String settingName) {
        Map<String, Setting> settings = moduleSettings.get(moduleName);
        return settings != null ? settings.get(settingName) : null;
    }

    public static boolean getBool(String moduleName, String settingName) {
        Setting setting = getSetting(moduleName, settingName);
        return setting != null ? setting.BoolValue() : false;
    }

    public static double getSlider(String moduleName, String settingName) {
        Setting setting = getSetting(moduleName, settingName);
        return setting != null ? setting.SliderValue() : 0.0;
    }
}