package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.Island;
import utils.lists.Passive;
import utils.render.ESPUtil;
import utils.skyblock.AreaUtil;

import java.awt.*;
import java.util.Random;
import java.util.*;
import java.util.List;

public class Pelt extends Modules {
    private static final Random random = new Random();

    private static final String AutoWarpback = "Warp back";
    private static final String Withline = "line";
    private static final String warpdelay = "Warp Delay";

    public Pelt() {
        super("Pelt Helper", "Render");

        Modules.registerSetting(this, new Setting(AutoWarpback, "Makes you automatically warp back", false));
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
        Modules.registerSetting(this, new Setting(warpdelay, "Delay before warping back + 401 ms", 1, 1, 500));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Island currentArea = AreaUtil.getArea();
        if (currentArea != Island.FarmingIsland) {
            return;
        }

        List<Entity> loadedEntities = Minecraft.getMinecraft().theWorld.loadedEntityList;
        for (Entity entity : loadedEntities) {
            if (entity instanceof EntityArmorStand) {
                String displayName = entity.getDisplayName().getUnformattedText().toLowerCase();
                Passive matchingPassive = Arrays.stream(Passive.values())
                        .filter(passive -> displayName.contains(passive.name().toLowerCase()))
                        .findFirst()
                        .orElse(null);

                if (matchingPassive != null) {
                    Entity mobEntity = ESPUtil.findMobEntityBelow(Minecraft.getMinecraft(), entity, matchingPassive.getEntityClass());
                    if (mobEntity != null) {
                        ESPUtil.Esp(mobEntity, 2, Color.WHITE, 1f, Modules.getBool("Pelt Helper", Withline), false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        Island currentArea = AreaUtil.getArea();
        if (currentArea != Island.FarmingIsland) {
            return;
        }

        if (!Modules.getBool("Pelt Helper", AutoWarpback)) {
            return;
        }

        String message = event.message.getUnformattedText().replaceAll("§.", "");

        if (message.contains("Return to the Trapper soon to get a new animal to hunt!")) {
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(401) + Modules.getSlider("Pelt Helper", warpdelay));
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp trapper");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}