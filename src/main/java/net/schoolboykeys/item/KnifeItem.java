package net.schoolboykeys.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.schoolboykeys.init.ModItems;

/**
 * Нож. Кладётся на пол как ключи. Фейерверк можно разрезать:
 * 1) ПКМ ножом по лежащему фейерверку; 2) нож в основной руке, фейерверк в левой, ПКМ в воздух.
 */
public class KnifeItem extends BlockItem {
    public KnifeItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack knife = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack off = player.getOffhandItem();
            if (off.is(ModItems.FIREWORK.get())) {
                if (!level.isClientSide) {
                    off.shrink(1);
                    give(player, new ItemStack(ModItems.FIREWORK_CUT.get()));
                    give(player, new ItemStack(Items.GUNPOWDER, 1 + level.random.nextInt(2)));
                    level.playSound(null, player.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.displayClientMessage(Component.translatable("message.schoolboykeys.firework_cut"), true);
                }
                return InteractionResultHolder.sidedSuccess(knife, level.isClientSide);
            }
        }
        return super.use(level, player, hand);
    }

    private static void give(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}
