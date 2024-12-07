package utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import utils.Utils;

import Events.game.RenderPartialTicks;
import utils.misc.MathUtil;

import java.awt.*;

public class ESPUtil implements Utils {
    public static double[] getInterpolatedPos(Entity entity) {
        if (entity == null) {
            return new double[]{0, 0, 0};
        }
        float ticks = RenderPartialTicks.getPartialTicks();
        return new double[]{
                MathUtil.interpolate(entity.lastTickPosX, entity.posX, ticks) - mc.getRenderManager().viewerPosX,
                MathUtil.interpolate(entity.lastTickPosY, entity.posY, ticks) - mc.getRenderManager().viewerPosY,
                MathUtil.interpolate(entity.lastTickPosZ, entity.posZ, ticks) - mc.getRenderManager().viewerPosZ
        };
    }

    public static double[] getInterpolatedBlockPos(BlockPos blockPos, double lastTickX, double lastTickY, double lastTickZ) {
        if (blockPos == null) {
            return new double[]{0, 0, 0};
        }
        float ticks = RenderPartialTicks.getPartialTicks();
        double x = MathUtil.interpolate(lastTickX, blockPos.getX(), ticks) - mc.getRenderManager().viewerPosX;
        double y = MathUtil.interpolate(lastTickY, blockPos.getY(), ticks) - mc.getRenderManager().viewerPosY;
        double z = MathUtil.interpolate(lastTickZ, blockPos.getZ(), ticks) - mc.getRenderManager().viewerPosZ;

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
        AxisAlignedBB searchBox = armorStand.getEntityBoundingBox().offset(0, -1, 0).expand(0.5, 1, 0.5);
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity != armorStand && entity.getEntityBoundingBox().intersectsWith(searchBox)) {
                if (entityClass.isInstance(entity)) {
                    return entity;
                }
            }
        }
        return null;
    }

    public static void Esp(Entity entity, int width, Color color, float opacity, boolean tracer, boolean filled) {
        if (entity == null) {
            return;
        }

        if (filled) {
            RenderUtil.renderFilledBB((EntityLivingBase) entity, color, opacity);
        } else {
            RenderUtil.renderBB((EntityLivingBase) entity, color, opacity);
        }

        if (tracer) {
            RenderUtil.renderTracer(entity, width, color, opacity);
        }
    }
}