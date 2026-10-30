package net.schoolboykeys.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.schoolboykeys.init.ModBlockEntities;
import net.schoolboykeys.util.KeyData;

/** Запоминает номер ключа, пока ключ лежит на полу. */
public class KeyBlockEntity extends BlockEntity {
    private String keyId = "";

    public KeyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KEY_BLOCK.get(), pos, state);
    }

    public void readFrom(ItemStack stack) {
        String id = KeyData.get(stack);
        this.keyId = id == null ? "" : id;
        this.setChanged();
    }

    public void applyTo(ItemStack stack) {
        if (!this.keyId.isEmpty()) {
            KeyData.set(stack, this.keyId);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.keyId.isEmpty()) {
            tag.putString(KeyData.TAG, this.keyId);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.keyId = tag.getString(KeyData.TAG);
    }
}
