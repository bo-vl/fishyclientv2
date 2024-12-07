package modules.Combat;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import utils.Utils;
import utils.lists.Island;
import utils.misc.NBTUtil;
import utils.render.ESPUtil;
import utils.skyblock.AreaUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Voidgloom extends Modules implements Utils {
    private static final String Withline = "line";
    private static final String SkullEsp = "skull";
    private static final String SKULL_TEXTURE = "eyJ0aW1lc3RhbXAiOjE1MzQ5NjM0MzU5NjIsInByb2ZpbGVJZCI6ImQzNGFhMmI4MzFkYTRkMjY5NjU1ZTMzYzE0M2YwOTZjIiwicHJvZmlsZU5hbWUiOiJFbmRlckRyYWdvbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWIwNzU5NGUyZGYyNzM5MjFhNzdjMTAxZDBiZmRmYTExMTVhYmVkNWI5YjIwMjllYjQ5NmNlYmE5YmRiYjRiMyJ9fX0=";

    public Voidgloom() {
        super("Voidgloom", "Combat");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
        Modules.registerSetting(this, new Setting(SkullEsp, "Esp Custom Head", false));
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
                .filter(stand -> stand.getName().contains(playerName)).collect(Collectors.toList());

        for (EntityArmorStand stand : matchingArmorStands) {
            Entity enderman = ESPUtil.findMobEntityBelow(mc, stand, EntityEnderman.class);

            if (enderman != null) {
                ESPUtil.Esp(enderman, 2, Color.WHITE, 1f, Modules.getBool("Voidgloom", Withline), false);
            }
        }

        List<EntityArmorStand> customSkullStands = mc.theWorld.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityArmorStand)
                .map(entity -> (EntityArmorStand) entity)
                .filter(stand -> {
                    ItemStack helmet = stand.getCurrentArmor(3);
                    if (helmet == null) return false;
                    String texture = NBTUtil.getSkullTexture(helmet);
                    return texture != null && texture.equals(SKULL_TEXTURE);
                })
                .collect(Collectors.toList());

        for (EntityArmorStand stand : customSkullStands) {
            ESPUtil.Esp(stand, 2, Color.WHITE, 1f, Modules.getBool("Voidgloom", Withline), false);
        }
    }
}