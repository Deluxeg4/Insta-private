package instadevelopment.kiriyaga.insta.mixininterface;

import net.minecraft.world.entity.Entity;

public interface IEntityRenderState {
    Entity getEntity();
    void setEntity(Entity entity);
}
