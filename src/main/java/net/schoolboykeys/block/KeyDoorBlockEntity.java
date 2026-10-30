package net.schoolboykeys.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.schoolboykeys.init.ModBlockEntities;
import net.schoolboykeys.util.KeyData;

/** Данные двери: к какому ключу привязана и заперта ли. Живёт в нижней половине двери. */
public class KeyDoorBlockEntity extends BlockEntity {
    private String keyId = "";
    private boolean locked = false;

    public KeyDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KEY_DOOR.get(), pos, state);
    }

    public boolean hasKey() {
        return !this.keyId.isEmpty();
    }

    public boolean matches(String id) {
        return id != null && this.hasKey() && this.keyId.equals(id);
    }

    public String getKeyId() {
        return this.keyId;
    }

    public void setKeyId(String id) {
        this.keyId = id;
        this.setChanged();
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString(KeyData.TAG, this.keyId);
        tag.putBoolean("Locked", this.locked);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.keyId = tag.getString(KeyData.TAG);
        this.locked = tag.getBoolean("Locked");
    }
}
