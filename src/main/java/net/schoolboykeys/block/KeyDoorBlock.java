package net.schoolboykeys.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.schoolboykeys.init.ModSounds;
import net.schoolboykeys.item.ColorKeyItem;
import net.schoolboykeys.util.KeyData;

/**
 * Дверь с замком.
 * <ul>
 *   <li>ПКМ любым ключом по свободной двери — привязать дверь к этому ключу (дверь закрывается и запирается).</li>
 *   <li>ПКМ подходящим ключом по запертой двери — отпереть и открыть.</li>
 *   <li>Shift+ПКМ подходящим ключом — запереть / отпереть.</li>
 *   <li>Ключ другого цвета/номера не подходит.</li>
 * </ul>
 */
public class KeyDoorBlock extends DoorBlock implements EntityBlock {

    public KeyDoorBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY), BlockSetType.IRON);
    }

    private static BlockPos lowerPos(BlockState state, BlockPos pos) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new KeyDoorBlockEntity(pos, state) : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        BlockPos lower = lowerPos(state, pos);
        if (!(level.getBlockEntity(lower) instanceof KeyDoorBlockEntity door)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack held = player.getItemInHand(hand);
        boolean holdsKey = held.getItem() instanceof ColorKeyItem;
        boolean sneaking = player.isShiftKeyDown();
        BlockState lowerState = level.getBlockState(lower);

        // 1. Дверь ещё никому не назначена
        if (!door.hasKey()) {
            if (holdsKey) {
                String id = KeyData.ensure(held);
                door.setKeyId(id);
                this.setOpen(player, level, lowerState, lower, false);
                door.setLocked(true);
                level.playSound(null, lower, ModSounds.DOOR_UNLOCK.get(), SoundSource.BLOCKS, 1.0F, 0.6F);
                player.displayClientMessage(
                        Component.translatable("message.schoolboykeys.door_bound", KeyData.shortId(id)), true);
                return InteractionResult.CONSUME;
            }
            this.setOpen(player, level, lowerState, lower, !lowerState.getValue(OPEN));
            return InteractionResult.CONSUME;
        }

        // 2. Дверь назначена
        boolean matches = holdsKey && door.matches(KeyData.get(held));

        if (matches && sneaking) {
            if (door.isLocked()) {
                door.setLocked(false);
                level.playSound(null, lower, ModSounds.DOOR_UNLOCK.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                player.displayClientMessage(Component.translatable("message.schoolboykeys.door_unlocked"), true);
            } else {
                this.setOpen(player, level, lowerState, lower, false);
                door.setLocked(true);
                level.playSound(null, lower, ModSounds.DOOR_UNLOCK.get(), SoundSource.BLOCKS, 1.0F, 0.6F);
                player.displayClientMessage(Component.translatable("message.schoolboykeys.door_locked"), true);
            }
            return InteractionResult.CONSUME;
        }

        if (door.isLocked()) {
            if (matches) {
                door.setLocked(false);
                this.setOpen(player, level, lowerState, lower, true);
                player.displayClientMessage(Component.translatable("message.schoolboykeys.door_unlocked"), true);
            } else {
                level.playSound(null, lower, ModSounds.DOOR_LOCKED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                player.displayClientMessage(Component.translatable(
                        holdsKey ? "message.schoolboykeys.wrong_key" : "message.schoolboykeys.door_is_locked"), true);
            }
            return InteractionResult.CONSUME;
        }

        this.setOpen(player, level, lowerState, lower, !lowerState.getValue(OPEN));
        return InteractionResult.CONSUME;
    }

    /** Запертую дверь нельзя открыть редстоуном. */
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.getBlockEntity(lowerPos(state, pos)) instanceof KeyDoorBlockEntity door && door.isLocked()) {
            return;
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }
}
