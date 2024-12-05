package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.Passive;
import utils.lists.PeltTypes;
import utils.render.ESPUtil;

import java.awt.*;
import java.util.Random;

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
        for (PeltTypes type : PeltTypes.values()) {
            for (Passive passive : Passive.values()) {
                for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    if (entity instanceof EntityArmorStand && entity.getDisplayName().getUnformattedText().toLowerCase().contains(passive.name().toLowerCase()) && entity.getDisplayName().getUnformattedText().toLowerCase().contains(type.name().toLowerCase())) {
                        ESPUtil.Esp(entity, 2, Color.WHITE, 1f, Modules.getBool("Pelt Helper", Withline), false, null, true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
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