package modules.Render;

import Events.game.SpawnParticleEvent;
import gui.Modules;
import gui.element.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.render.RenderUtil;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static utils.misc.MathUtil.basicallyEqual;

public class Endernode extends Modules {
    public static Map<BlockPos, Integer> positions = new ConcurrentHashMap<>();
    private static final int HIGHLIGHT_DURATION = 150;
    private static final int COOLDOWN_PERIOD = 20;

    private static final String Withline = "line";
    public Endernode() {
        super("Endernode ESP", "Render");
        Modules.registerSetting(this, new Setting(Withline, "Draws a line to the block", false));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.theWorld == null) return;

        positions.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            int ticks = entry.getValue() - 1;

            IBlockState state = mc.theWorld.getBlockState(pos);
            if (state == null || (state.getBlock() != Blocks.end_stone && state.getBlock() != Blocks.obsidian) || ticks <= 0) {
                return true;
            } else {
                entry.setValue(ticks);
                return false;
            }
        });
    }

    @SubscribeEvent
    public void onParticleSpawn(SpawnParticleEvent event) {
        if (event.particleType == EnumParticleTypes.PORTAL) {
            double x = event.position.xCoord;
            double y = event.position.yCoord;
            double z = event.position.zCoord;

            boolean xZero = basicallyEqual((x - 0.5) % 1, 0, 0.2);
            boolean yZero = basicallyEqual((y - 0.5) % 1, 0, 0.2);
            boolean zZero = basicallyEqual((z - 0.5) % 1, 0, 0.2);

            if (xZero && zZero) {
                if (Math.abs(y % 1) == 0.25) {
                    addPositionIfValid(x, y - 1, z);
                } else if (Math.abs(y % 1) == 0.75) {
                    addPositionIfValid(x, y + 1, z);
                }
            }
            if (yZero && zZero) {
                if (Math.abs(x % 1) == 0.25) {
                    addPositionIfValid(x + 1, y, z);
                } else if (Math.abs(x % 1) == 0.75) {
                    addPositionIfValid(x - 1, y, z);
                }
            }
            if (yZero && xZero) {
                if (Math.abs(z % 1) == 0.25) {
                    addPositionIfValid(x, y, z + 1);
                } else if (Math.abs(z % 1) == 0.75) {
                    addPositionIfValid(x, y, z - 1);
                }
            }
        }
    }

    private void addPositionIfValid(double x, double y, double z) {
        BlockPos pos = new BlockPos(x, y, z);
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if (block == Blocks.end_stone || block == Blocks.obsidian) {
            positions.put(pos, HIGHLIGHT_DURATION + COOLDOWN_PERIOD);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (BlockPos pos : positions.keySet()) {
            RenderUtil.RenderBlock(pos, Color.WHITE, 1);
            if (Modules.getBool("Endernode ESP", Withline)) {
                RenderUtil.RenderTracerBlock(pos, 1, Color.WHITE, 1);
            }
        }
    }
}