package net.schoolboykeys.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.schoolboykeys.KeysMod;
import net.schoolboykeys.init.ModItems;
import net.schoolboykeys.init.ModSounds;

/**
 * Снотворное можно засыпать в кружку из SchoolBoyRunaway (блок schoolboyrunaway:bluecup):
 * ПКМ по кружке со снотворным в руке даёт звук и сообщение, как со слабительным. Таблетка не тратится.
 * Оригинальный мод менять не нужно; если его нет, обработчик просто ничего не находит.
 */
@Mod.EventBusSubscriber(modid = KeysMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MugEvents {
    private static final ResourceLocation MUG = new ResourceLocation("schoolboyrunaway", "bluecup");

    private MugEvents() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        if (!event.getItemStack().is(ModItems.SLEEPING_PILLS.get())) {
            return;
        }
        Level level = event.getLevel();
        Block block = level.getBlockState(event.getPos()).getBlock();
        if (!MUG.equals(ForgeRegistries.BLOCKS.getKey(block))) {
            return;
        }
        if (!level.isClientSide) {
            Player player = event.getEntity();
            level.playSound(null, event.getPos(), ModSounds.PILLS_POUR.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable("message.schoolboykeys.pills_sleeping"), true);
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
    }
}
