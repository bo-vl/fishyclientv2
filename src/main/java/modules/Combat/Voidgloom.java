package modules.Combat;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import utils.Utils;
import utils.lists.Island;
import utils.render.ESPUtil;
import utils.skyblock.AreaUtil;
import utils.skyblock.SlayerUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Voidgloom extends Modules implements Utils {
    private static final String Withline = "line";
    private List<EntityArmorStand> armorStandsCache;

    public Voidgloom() {
        super("Voidgloom", "Combat");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (armorStandsCache == null || armorStandsCache.isEmpty()) {
            armorStandsCache = mc.theWorld.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityArmorStand)
                    .map(entity -> (EntityArmorStand) entity)
                    .collect(Collectors.toList());
        }

        Entity armorStand = armorStandsCache.stream().findFirst().orElse(null);
        if (armorStand == null) {
            return;
        }

        Island currentArea = AreaUtil.getArea();
        if (currentArea == Island.TheEnd) {
            Entity slayer = SlayerUtil.getSlayer(mc, armorStand, "Enderman");
            if (slayer != null) {
                ESPUtil.Esp(slayer, 2, Color.WHITE, 1f, Modules.getBool("Voidgloom", Withline), false, null, true);
            }
        }
    }
}