package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.Island;
import utils.lists.PestTypes;
import utils.render.ESPUtil;
import utils.skyblock.AreaUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Pest extends Modules {
    private static final String Withline = "line";

    public Pest() {
        super("Pest Helper", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Island currentArea = AreaUtil.getArea();
        if (currentArea != Island.Garden) {
            return;
        }

        List<Entity> loadedEntities = mc.theWorld.loadedEntityList;
        for (Entity entity : loadedEntities) {
            if (entity instanceof EntityArmorStand) {
                String entityName = entity.getName().toLowerCase();
                if (Arrays.stream(PestTypes.values())
                        .anyMatch(type -> entityName.contains(type.name().toLowerCase()))) {

                    ESPUtil.Esp(entity, 2, Color.WHITE, 1f,
                            Modules.getBool("Pest Helper", Withline),
                            true, entityName, false);
                }
            }
        }
    }
}