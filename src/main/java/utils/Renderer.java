package utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Renderer {
    private static final Color DEFAULT_COLOR = new Color(0, 255, 0, 128);
    private static final float DEFAULT_LINE_WIDTH = 2.0f;
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static void renderEntityBoundingBox(Entity entity, float partialTicks) {
        if (!validateRenderContext()) return;

        AxisAlignedBB boundingBox = calculateInterpolatedBoundingBox(entity, partialTicks);

        setupRenderState();
        try {
            renderBoundingBox(boundingBox);
        } finally {
            restoreRenderState();
        }
    }

    public static void renderBlockBoundingBox(BlockPos pos, float partialTicks) {
        if (!validateRenderContext()) return;

        AxisAlignedBB boundingBox = new AxisAlignedBB(
                pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
        );

        setupRenderState();
        try {
            renderBoundingBox(boundingBox);
        } finally {
            restoreRenderState();
        }
    }

    public static void renderLineToEntity(Entity entity, float partialTicks) {
        if (!validateRenderContext()) return;

        EntityPlayerSP player = MC.thePlayer;

        Vector3d playerPos = calculateInterpolatedPosition(player, partialTicks, true);
        Vector3d entityPos = calculateInterpolatedPosition(entity, partialTicks, false);

        setupRenderState();
        try {
            renderLine(playerPos, entityPos);
        } finally {
            restoreRenderState();
        }
    }

    private static boolean validateRenderContext() {
        return MC != null && MC.getRenderManager() != null && MC.theWorld != null;
    }

    private static void setupRenderState() {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA,
                GL11.GL_ONE_MINUS_SRC_ALPHA,
                GL11.GL_ONE,
                GL11.GL_ZERO
        );
        GL11.glLineWidth(DEFAULT_LINE_WIDTH);
        GlStateManager.color(
                DEFAULT_COLOR.getRed() / 255.0F,
                DEFAULT_COLOR.getGreen() / 255.0F,
                DEFAULT_COLOR.getBlue() / 255.0F,
                DEFAULT_COLOR.getAlpha() / 255.0F
        );
    }

    private static void restoreRenderState() {
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
    }

    private static void renderBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.pushMatrix();
        GlStateManager.translate(
                -MC.getRenderManager().viewerPosX,
                -MC.getRenderManager().viewerPosY,
                -MC.getRenderManager().viewerPosZ
        );

        worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        addBoundingBoxVertices(worldRenderer, boundingBox);
        tessellator.draw();

        GlStateManager.popMatrix();
    }

    private static void renderLine(Vector3d start, Vector3d end) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.pushMatrix();

        worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        addLineVertices(worldRenderer, start, end);
        tessellator.draw();

        GlStateManager.popMatrix();
    }

    private static AxisAlignedBB calculateInterpolatedBoundingBox(Entity entity, float partialTicks) {
        double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double y = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
        double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

        return entity.getEntityBoundingBox().offset(-entity.posX + x, -entity.posY + y, -entity.posZ + z);
    }

    private static Vector3d calculateInterpolatedPosition(Entity entity, float partialTicks, boolean isPlayer) {
        double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double y = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
        double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;

        return new Vector3d(
                x - MC.getRenderManager().viewerPosX,
                y + (isPlayer ? entity.getEyeHeight() : entity.height / 2.0) - MC.getRenderManager().viewerPosY,
                z - MC.getRenderManager().viewerPosZ
        );
    }

    private static void addBoundingBoxVertices(WorldRenderer buffer, AxisAlignedBB bb) {
        float r = DEFAULT_COLOR.getRed() / 255.0F;
        float g = DEFAULT_COLOR.getGreen() / 255.0F;
        float b = DEFAULT_COLOR.getBlue() / 255.0F;
        float a = DEFAULT_COLOR.getAlpha() / 255.0F;

        buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();

        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();

        buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
    }

    private static void addLineVertices(WorldRenderer buffer, Vector3d start, Vector3d end) {
        float r = DEFAULT_COLOR.getRed() / 255.0F;
        float g = DEFAULT_COLOR.getGreen() / 255.0F;
        float b = DEFAULT_COLOR.getBlue() / 255.0F;
        float a = DEFAULT_COLOR.getAlpha() / 255.0F;

        buffer.pos(start.x, start.y, start.z).color(r, g, b, a).endVertex();
        buffer.pos(end.x, end.y, end.z).color(r, g, b, a).endVertex();
    }

    private static class Vector3d {
        final double x, y, z;

        Vector3d(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}