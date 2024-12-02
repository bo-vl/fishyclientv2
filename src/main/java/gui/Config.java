package gui;

import gui.element.Category;
import gui.element.Module;
import gui.element.Setting;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "FishClient.cfg";
    private static Map<String, Boolean> moduleStates = new HashMap<>();
    private static Map<String, Boolean> categoryStates = new HashMap<>();
    private static Map<String, Integer> SliderValues = new HashMap<>();
    private static Map<String, Boolean> ToggleValues = new HashMap<>();

    public static void saveModuleState(String name, boolean state) {
        moduleStates.put(name, state);
    }

    public static boolean loadModuleState(String name) {
        return moduleStates.getOrDefault(name, false);
    }

    public static void saveCategoryState(String name, boolean state) {
        categoryStates.put(name, state);
    }

    public static boolean loadCategoryState(String name) {
        return categoryStates.getOrDefault(name, false);
    }

    public static void saveSliderValue(String name, int value) {
        SliderValues.put(name, value);
    }

    public static int loadSliderValue(String name) {
        return SliderValues.getOrDefault(name, 0);
    }

    public static void saveToggleValue(String name, boolean value) {
        ToggleValues.put(name, value);
    }

    public static boolean loadToggleValue(String name) {
        return ToggleValues.getOrDefault(name, false);
    }

    public static void save() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            try (OutputStream output = new FileOutputStream(configFile)) {
                moduleStates.forEach((key, value) -> properties.setProperty("module." + key, Boolean.toString(value)));
                categoryStates.forEach((key, value) -> properties.setProperty("category." + key, Boolean.toString(value)));
                SliderValues.forEach((key, value) -> properties.setProperty("slider." + key, Integer.toString(value)));
                ToggleValues.forEach((key, value) -> properties.setProperty("toggle." + key, Boolean.toString(value)));
                properties.store(output, null);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void load() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            properties.forEach((key, value) -> {
                String keyStr = (String) key;
                if (keyStr.startsWith("module.")) {
                    moduleStates.put(keyStr.substring(7), Boolean.parseBoolean((String) value));
                } else if (keyStr.startsWith("category.")) {
                    categoryStates.put(keyStr.substring(9), Boolean.parseBoolean((String) value));
                } else if (keyStr.startsWith("slider.")) {
                    SliderValues.put(keyStr.substring(7), Integer.parseInt((String) value));
                } else if (keyStr.startsWith("toggle.")) {
                    ToggleValues.put(keyStr.substring(7), Boolean.parseBoolean((String) value));
                }
            });
            properties.forEach((key, value) -> System.out.println(key + ": " + value));

            for (Category category : Category.getAllCategories()) {
                boolean categoryState = loadCategoryState(category.getName());
                if (categoryState != category.isExpanded()) {
                    category.toggleExpansion();
                }
            }

            for (Module module : Modules.getAllModules()) {
                boolean moduleState = loadModuleState(module.getName());
                if (moduleState != module.isEnabled()) {
                    module.toggle();
                }
                for (Setting setting : module.getSettings()) {
                    if (setting.getType() == Setting.SettingsType.BOOLEAN) {
                        setting.setBoolValue(loadToggleValue(setting.getName()));
                    } else if (setting.getType() == Setting.SettingsType.SLIDER) {
                        setting.setSliderValue(loadSliderValue(setting.getName()));
                    }
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}