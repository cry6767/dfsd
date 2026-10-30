package net.schoolboykeys.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.schoolboykeys.util.KeyData;

/** Цветной ключ. Это BlockItem: ключ можно положить на пол, как в основном моде. */
public class ColorKeyItem extends BlockItem {
    public ColorKeyItem(Block block, Properties properties) {
        super(block, properties);
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
