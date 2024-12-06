package modules.Combat;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import utils.Utils;
import utils.lists.Island;
import utils.render.ESPUtil;
import utils.skyblock.AreaUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static utils.render.ESPUtil.findMobEntityBelow;

public class Voidgloom extends Modules implements Utils {
    private static final String Withline = "line";
    private List<EntityArmorStand> armorStandsCache;

    public Voidgloom() {
        super("Voidgloom", "Combat");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
    }


    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Island currentArea = AreaUtil.getArea();
        if (currentArea != Island.TheEnd) {
            return;
        }
        String playerName = mc.thePlayer.getName();

        List<EntityArmorStand> matchingArmorStands = mc.theWorld.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityArmorStand)
                .map(entity -> (EntityArmorStand) entity)
                .filter(stand -> stand.getName().contains(playerName))
                .collect(Collectors.toList());

        for (EntityArmorStand stand : matchingArmorStands) {
            Entity enderman = ESPUtil.findMobEntityBelow(mc, stand, EntityEnderman.class);

            if (enderman != null) {
                ESPUtil.Esp(enderman, 2, Color.WHITE, 1f,
                        Modules.getBool("Voidgloom", Withline),
                        false,   // Added fill
                        null,
                        false);
            }
        }
    }
}