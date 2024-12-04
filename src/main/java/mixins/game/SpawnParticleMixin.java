package mixins.game;

import Events.SpawnParticleEvent;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class SpawnParticleMixin {
    @Inject(method = "spawnParticle(Lnet/minecraft/util/EnumParticleTypes;ZDDDDDD[I)V", at = @At("HEAD"), cancellable = true)
    private void onSpawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] p_175682_15_, CallbackInfo ci) {
        SpawnParticleEvent spawnParticleEvent = new SpawnParticleEvent(particleType);
        spawnParticleEvent.position = new Vec3(xCoord, yCoord, zCoord);
        spawnParticleEvent.offset = new Vec3(xOffset, yOffset, zOffset);
        MinecraftForge.EVENT_BUS.post(spawnParticleEvent);
        if (spawnParticleEvent.isCanceled()) {
            ci.cancel();
        }
    }
}
