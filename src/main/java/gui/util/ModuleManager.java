package gui.util;

import modules.pelt.AutoWarpBack;
import gui.element.Module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleManager {
    private static Map<String, Module> modules = new HashMap<>();

    static {
        registerModule(new AutoWarpBack());
    }

    public static void registerModule(Module module) {
        modules.put(module.getName(), module);
    }

    public static Module getModule(String name) {
        return modules.get(name);
    }

    public static List<Module> getModulesByCategory(String category) {
        return modules.values().stream()
                .filter(module -> module.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public static Map<String, Module> getModules() {
        return modules;
    }
}