package utils.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;

import static org.lwjgl.opengl.GL11.*;

public class GLUtil {
    public static int[] enabledCaps = new int[32];

    public static void enableDepth() {
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
    }

    public static void disableDepth(){
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
    }

    public static void enableCaps(int... caps) {
        for (int cap : caps) glEnable(cap); {
            enabledCaps = caps;
        }
    }

    public static void disableCaps() {
        for (int cap : enabledCaps) glDisable(cap);
    }

    public static void startBlend() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void endBlend() {
        GlStateManager.disableBlend();
    }

    public static void setup2DRendering(boolean blend) {
        if (blend) {
            startBlend();
        }
        GlStateManager.disableTexture2D();
    }

    public static void setup2DRendering() {
        setup2DRendering(true);
    }

    public static void end2DRendering() {
        GlStateManager.enableTexture2D();
        endBlend();
    }

    public static void startRotate(float x, float y, float rotate) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.rotate(rotate, 0, 0, -1);
        GlStateManager.translate(-x, -y, 0);
    }

    public static void endRotate() {
        GlStateManager.popMatrix();
    }

    public static void renderBoundingBox(AxisAlignedBB bb) {
        glBegin(GL_LINES);

        glVertex3d(bb.minX, bb.minY, bb.minZ);
        glVertex3d(bb.maxX, bb.minY, bb.minZ);
        glVertex3d(bb.maxX, bb.minY, bb.minZ);
        glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        glVertex3d(bb.minX, bb.minY, bb.maxZ);
        glVertex3d(bb.minX, bb.minY, bb.maxZ);
        glVertex3d(bb.minX, bb.minY, bb.minZ);

        glVertex3d(bb.minX, bb.maxY, bb.minZ);
        glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        glVertex3d(bb.minX, bb.maxY, bb.minZ);

        glVertex3d(bb.minX, bb.minY, bb.minZ);
        glVertex3d(bb.minX, bb.maxY, bb.minZ);
        glVertex3d(bb.maxX, bb.minY, bb.minZ);
        glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        glVertex3d(bb.minX, bb.minY, bb.maxZ);
        glVertex3d(bb.minX, bb.maxY, bb.maxZ);

        glEnd();
    }

    public static void renderBlockBox(int x, int y, int z) {
        glBegin(GL_LINES);

        glVertex3d(x, y, z);
        glVertex3d(x + 1, y, z);
        glVertex3d(x + 1, y, z);
        glVertex3d(x + 1, y, z + 1);
        glVertex3d(x + 1, y, z + 1);
        glVertex3d(x, y, z + 1);
        glVertex3d(x, y, z + 1);
        glVertex3d(x, y, z);

        glVertex3d(x, y + 1, z);
        glVertex3d(x + 1, y + 1, z);
        glVertex3d(x + 1, y + 1, z);
        glVertex3d(x + 1, y + 1, z + 1);
        glVertex3d(x + 1, y + 1, z + 1);
        glVertex3d(x, y + 1, z + 1);
        glVertex3d(x, y + 1, z + 1);
        glVertex3d(x, y + 1, z);

        glVertex3d(x, y, z);
        glVertex3d(x, y + 1, z);
        glVertex3d(x + 1, y, z);
        glVertex3d(x + 1, y + 1, z);
        glVertex3d(x + 1, y, z + 1);
        glVertex3d(x + 1, y + 1, z + 1);
        glVertex3d(x, y, z + 1);
        glVertex3d(x, y + 1, z + 1);

        glEnd();
    }
}