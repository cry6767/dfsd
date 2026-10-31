package net.schoolboykeys.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.schoolboykeys.block.KeyDoorBlock;
import net.schoolboykeys.util.KeyData;

/** Цветной ключ. Это BlockItem: ключ можно положить на пол, как в основном моде. */
public class ColorKeyItem extends BlockItem {
    public ColorKeyItem(Block block, Properties properties) {
        super(block, properties);
    }

    /** Shift+ПКМ по двери с замком должен дойти до двери, а не поставить ключ. */
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return level.getBlockState(pos).getBlock() instanceof KeyDoorBlock;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String id = KeyData.get(stack);
        if (id == null) {
            tooltip.add(Component.translatable("tooltip.schoolboykeys.key_unbound").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.schoolboykeys.key_id", KeyData.shortId(id)).withStyle(ChatFormatting.GRAY));
        }
    }
}
