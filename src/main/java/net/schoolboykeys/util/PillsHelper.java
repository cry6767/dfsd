package net.schoolboykeys.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.schoolboykeys.init.ModItems;
import net.schoolboykeys.init.ModSounds;
import net.schoolboykeys.init.ModTags;

public final class PillsHelper {
    private PillsHelper() {
    }

    /** Засыпать таблетки (звук + сообщение, таблетка не тратится). true, если в руке таблетки. */
    public static boolean tryPour(Level level, BlockPos pos, Player player, ItemStack held) {
        if (!held.is(ModTags.PILLS)) {
            return false;
        }
        level.playSound(null, pos, ModSounds.PILLS_POUR.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        player.displayClientMessage(Component.translatable(
                held.is(ModItems.SLEEPING_PILLS.get())
                        ? "message.schoolboykeys.pills_sleeping"
                        : "message.schoolboykeys.pills_laxative"), true);
        return true;
    }
}
