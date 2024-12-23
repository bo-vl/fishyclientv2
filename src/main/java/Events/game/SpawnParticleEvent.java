package Events.game;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class SpawnParticleEvent extends Event {
    public EnumParticleTypes particleType;
    public Vec3 position;
    public Vec3 offset;

    public SpawnParticleEvent(EnumParticleTypes particleType) {
        this.particleType = particleType;
    }
}