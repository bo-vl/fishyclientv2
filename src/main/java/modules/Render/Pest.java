package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.PestTypes;
import utils.render.ESPUtil;

import java.awt.*;

public class Pest extends Modules {
    private static final String Withline = "line";
    public Pest() {
        super("Pest Helper", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (PestTypes type : PestTypes.values()) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                ESPUtil.Esp(entity, 2, Color.WHITE, 1f, Modules.getBool("Pest Helper", Withline), true, type.name().toLowerCase(), false);
            }
        }
    }
}
