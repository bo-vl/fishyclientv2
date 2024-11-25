package gui.util;

import gui.element.Setting;
import gui.element.Module;
import java.util.HashMap;
import java.util.Map;

public class ModuleSettings {
    private static final Map<String, Map<String, Setting>> moduleSettings = new HashMap<>();

    public static void registerSetting(Module module, Setting setting, String settingId) {
        moduleSettings.computeIfAbsent(module.getName(), k -> new HashMap<>())
                .put(settingId, setting);
        module.addSetting(setting);
    }

    public static boolean getBool(String moduleName, String settingId) {
        Setting setting = getSetting(moduleName, settingId);
        return setting != null ? setting.getBoolValue() : false;
    }

    public static double getSlider(String moduleName, String settingId) {
        Setting setting = getSetting(moduleName, settingId);
        return setting != null ? setting.getSliderValue() : 0.0;
    }

    private static Setting getSetting(String moduleName, String settingId) {
        Map<String, Setting> settings = moduleSettings.get(moduleName);
        return settings != null ? settings.get(settingId) : null;
    }
}