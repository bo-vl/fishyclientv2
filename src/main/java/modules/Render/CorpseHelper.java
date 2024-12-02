package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.CorpseTypes;
import utils.render.RenderUtil;

import java.awt.*;

public class CorpseHelper extends Modules {
    private static final String Withline = "line";

    public CorpseHelper() {
        super("Corpse Helper", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (CorpseTypes type : CorpseTypes.values()) {
            for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (entity instanceof EntityArmorStand) {
                    EntityArmorStand armorStand = (EntityArmorStand) entity;
                    if (armorStand.getEquipmentInSlot(4) != null) {
                        String helmetName = armorStand.getEquipmentInSlot(4).getDisplayName();
                        if (helmetName.contains(type.getItemName())) {
                            RenderUtil.renderBB(armorStand, Color.WHITE, 0.5f);
                            if (Modules.getBool("Corpse Helper", Withline)) {
                                RenderUtil.renderTracer(armorStand, 2, Color.WHITE, 0.5f);
                            }
                        }
                    }
                }
            }
        }
    }
}
