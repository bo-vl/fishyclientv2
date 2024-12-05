package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.CorpseTypes;
import utils.render.ESPUtil;
import utils.render.RenderUtil;

import java.awt.*;

public class Mineshaft extends Modules {
    private static final String Withline = "line";
    private static final String CorpseName = "Corpse name";
    private static final String TextScale = "Scale";

    public Mineshaft() {
        super("Mineshaft Helper", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
        Modules.registerSetting(this, new Setting(CorpseName, "Shows Corpse Name", false));
        Modules.registerSetting(this, new Setting(TextScale, "Text Scale", 1, 1, 5));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (CorpseTypes type : CorpseTypes.values()) {
            for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (entity instanceof EntityArmorStand) {
                    EntityArmorStand armorStand = (EntityArmorStand) entity;
                    if (armorStand.getEquipmentInSlot(4) != null) {
                        String helmetName = armorStand.getEquipmentInSlot(4).getDisplayName();
                        if (helmetName != null && helmetName.contains(type.getItemName())) {
                            if (Modules.getBool("Mineshaft Helper", CorpseName)) {
                                RenderUtil.renderFloatingText(type.name(), armorStand, Color.WHITE, (float) Modules.getSlider("Mineshaft Helper", TextScale), 0);
                            }
                            ESPUtil.Esp(armorStand, 2, Color.white, 0.5f, Modules.getBool("Mineshaft Helper", Withline), false, null, false);
                        }
                    }
                }
            }
        }
    }
}