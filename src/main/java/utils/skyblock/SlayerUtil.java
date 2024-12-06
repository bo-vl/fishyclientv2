package utils.skyblock;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import utils.Utils;

public class SlayerUtil implements Utils {
    public static Entity getSlayer(Minecraft mc, Entity armorStand, String mobName) {
        if (armorStand == null || !armorStand.getName().toLowerCase().contains(mc.thePlayer.getName().toLowerCase())) {
            return null;
        }
        AxisAlignedBB searchBox = armorStand.getEntityBoundingBox().offset(0, -1, 0).expand(1, 1, 1);
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity != armorStand && entity.getEntityBoundingBox().intersectsWith(searchBox)) {
                if (entity instanceof EntityLivingBase && entity.getName().toLowerCase().contains(mobName.toLowerCase())) {
                    return entity;
                }
            }
        }
        return null;
    }
}