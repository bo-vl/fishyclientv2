package gui.element;

import net.minecraftforge.common.MinecraftForge;

public class ToggleModule extends Module {
    private boolean registered = false;

    public ToggleModule(String name, String category) {
        super(name, category);
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
}