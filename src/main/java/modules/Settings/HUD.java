package modules.Settings;

import gui.Modules;
import gui.element.Module;
import gui.element.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.render.RenderUtil;
import utils.misc.ColorUtil;

import java.awt.*;
import java.util.List;

public class HUD extends Modules {
    private static String rainbow = "Rainbow";
    private static String RainbowSpeed = "Speed";

    public HUD() {
        super("HUD", "Settings");
        Modules.registerSetting(this, new Setting(rainbow, "Rainbow", false));
        Modules.registerSetting(this, new Setting(RainbowSpeed, "Speed", 1, 1, 10));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        renderHUD();
    }

    public void renderHUD() {
        if (!isEnabled()) return;
        float offset = 3;

        List<Module> modules = Modules.getAllModules();

        modules.sort((m1, m2) -> Integer.compare(m2.getName().length(), m1.getName().length()));

        for (Module module : modules) {
            if (module.isEnabled()) {
                if (Modules.getBool("HUD", rainbow)) {
                    Color rainbowColor = ColorUtil.rainbow(Modules.getSlider("HUD", RainbowSpeed) * 100, (int) offset, 1.0f, 1.0f, 1.0f);
                    RenderUtil.RenderText(module.getName(), 3, offset, rainbowColor);
                } else {
                    RenderUtil.RenderText(module.getName(), 3, offset, Color.WHITE);
                }
                offset += 10;
            }
        }
    }
}