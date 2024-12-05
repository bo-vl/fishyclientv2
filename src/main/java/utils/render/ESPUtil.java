package utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import utils.Utils;

import Events.game.RenderPartialTicks;
import utils.misc.MathUtils;

import java.awt.*;

public class ESPUtil implements Utils {
    public static double[] getInterpolatedPos(Entity entity) {
        if (entity == null) {
            return new double[]{0, 0, 0};
        }
        float ticks = RenderPartialTicks.getPartialTicks();
        return new double[]{
                MathUtils.interpolate(entity.lastTickPosX, entity.posX, ticks) - mc.getRenderManager().viewerPosX,
                MathUtils.interpolate(entity.lastTickPosY, entity.posY, ticks) - mc.getRenderManager().viewerPosY,
                MathUtils.interpolate(entity.lastTickPosZ, entity.posZ, ticks) - mc.getRenderManager().viewerPosZ
        };
    }

    public static double[] getInterpolatedBlockPos(BlockPos blockPos, double lastTickX, double lastTickY, double lastTickZ) {
        if (blockPos == null) {
            return new double[]{0, 0, 0};
        }
        float ticks = RenderPartialTicks.getPartialTicks();
        double x = MathUtils.interpolate(lastTickX, blockPos.getX(), ticks) - mc.getRenderManager().viewerPosX;
        double y = MathUtils.interpolate(lastTickY, blockPos.getY(), ticks) - mc.getRenderManager().viewerPosY;
        double z = MathUtils.interpolate(lastTickZ, blockPos.getZ(), ticks) - mc.getRenderManager().viewerPosZ;

        return new double[]{x, y, z};
    }

    public static double getDistance(double x, double y, double z) {
        double[] pos = getInterpolatedPos(mc.thePlayer);
        double diffX = x - pos[0];
        double diffY = y - pos[1];
        double diffZ = z - pos[2];
        return Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
    }

    public static AxisAlignedBB getInterpolatedBB(Entity entity) {
        if (entity == null) {
            return null;
        }
        final double[] renderingEntityPos = getInterpolatedPos(entity);
        final double entityRenderWidth = entity.width / 1.5;
        return new AxisAlignedBB(renderingEntityPos[0] - entityRenderWidth,
                renderingEntityPos[1], renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth,
                renderingEntityPos[1] + entity.height + (entity.isSneaking() ? -0.3 : 0.18), renderingEntityPos[2] + entityRenderWidth).expand(0.15, 0.15, 0.15);
    }

    public static Entity findMobEntityBelow(Minecraft mc, Entity armorStand, Class<? extends Entity> entityClass) {
        if (armorStand == null) {
            return null;
        }
        AxisAlignedBB searchBox = armorStand.getEntityBoundingBox().offset(0, -1, 0).expand(1, 1, 1);
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity != armorStand && entity.getEntityBoundingBox().intersectsWith(searchBox)) {
                if (entityClass.isInstance(entity)) {
                    return entity;
                }
            }
        }
        return null;
    }

    public static void Esp(Entity entity, int width, Color color, float opacity, boolean tracer, boolean WithName, String name, boolean ismob) {
        if (WithName) {
            if (!(entity instanceof EntityArmorStand)) {
                return;
            }

            String displayName = entity.getDisplayName().getUnformattedText().toLowerCase();

            if (!displayName.contains(name.toLowerCase())) {
                return;
            }

            entity = findMobEntityBelow(mc, entity, Entity.class);
        }

        if (ismob) {
            entity = findMobEntityBelow(mc, entity, EntityLivingBase.class);
            if (entity == null) {
                return;
            }
        }

        RenderUtil.renderBB((EntityLivingBase) entity, color, opacity);
        if (tracer) {
            RenderUtil.renderTracer(entity, width, color, opacity);
        }
    }
}