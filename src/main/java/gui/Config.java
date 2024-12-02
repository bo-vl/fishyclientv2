package gui;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static final String CONFIG_FILE = "FishClient.cfg";
    private static Map<String, Boolean> moduleStates = new HashMap<>();
    private static Map<String, Boolean> categoryStates = new HashMap<>();
    private static int enabledOutlineColor = Color.GREEN.getRGB();
    private static int disabledOutlineColor = Color.RED.getRGB();

    public static void saveModuleState(String moduleName, boolean state) {
        moduleStates.put(moduleName, state);
        saveConfig();
    }

    public static boolean loadModuleState(String moduleName) {
        return moduleStates.getOrDefault(moduleName, false);
    }

    public static void saveCategoryState(String categoryName, boolean state) {
        categoryStates.put(categoryName, state);
        saveConfig();
    }

    public static boolean loadCategoryState(String categoryName) {
        return categoryStates.getOrDefault(categoryName, false);
    }

    public static int getEnabledOutlineColor() {
        return enabledOutlineColor;
    }

    public static int getDisabledOutlineColor() {
        return disabledOutlineColor;
    }

    public static void loadConfig() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            moduleStates = (Map<String, Boolean>) ois.readObject();
            categoryStates = (Map<String, Boolean>) ois.readObject();
            enabledOutlineColor = ois.readInt();
            disabledOutlineColor = ois.readInt();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(moduleStates);
            oos.writeObject(categoryStates);
            oos.writeInt(enabledOutlineColor);
            oos.writeInt(disabledOutlineColor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}