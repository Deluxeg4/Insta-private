package instadevelopment.kiriyaga.insta.mixininterface;

import net.minecraft.world.phys.Vec3;

public interface IClientboundSetEntityMotionPacket {
    void setMovement(Vec3 vec);
}