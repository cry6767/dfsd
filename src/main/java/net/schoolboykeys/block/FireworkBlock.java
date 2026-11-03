package net.schoolboykeys.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.schoolboykeys.init.ModBlocks;
import net.schoolboykeys.init.ModItems;

/** Фейерверк на полу. ПКМ ножом — разрезать (получаешь порох), ПКМ рукой — забрать. */
public class FireworkBlock extends PropBlock {
    public FireworkBlock() {
        super(15.5D, 6.5D, 2.2D);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(ModItems.KNIFE.get())) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            BlockState cut = ModBlocks.FIREWORK_CUT.get().defaultBlockState().setValue(FACING, state.getValue(FACING));
            level.setBlock(pos, cut, 3);
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            ItemStack powder = new ItemStack(Items.GUNPOWDER, 1 + level.random.nextInt(2));
            if (!player.getInventory().add(powder)) {
                player.drop(powder, false);
            }
            player.displayClientMessage(Component.translatable("message.schoolboykeys.firework_cut"), true);
            return InteractionResult.CONSUME;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
