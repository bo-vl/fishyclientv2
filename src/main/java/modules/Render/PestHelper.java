package modules.Render;

import gui.Modules;
import gui.element.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.lists.PestTypes;
import utils.render.ESPUtil;
import utils.render.RenderUtil;

import java.awt.*;

public class PestHelper extends Modules {
    private static final String Withline = "line";
    public PestHelper() {
        super("Pest Helper", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the mob", false));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (PestTypes type : PestTypes.values()) {
            for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (entity instanceof EntityArmorStand && entity.getDisplayName().getUnformattedText().toLowerCase().contains(type.name().toLowerCase())) {
                    Entity pest = new ESPUtil().findMobEntityBelow(Minecraft.getMinecraft(), entity, EntityArmorStand.class);
                    RenderUtil.renderBB((EntityLivingBase) pest, Color.WHITE, 0.5f);
                    if (Modules.getBool("Pest Helper", Withline)) {
                        RenderUtil.renderTracer(pest, 2, Color.WHITE, 0.5f);
                    }
                }
            }
        }
    }
}
