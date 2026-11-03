package net.schoolboykeys.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.schoolboykeys.KeysMod;

public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KeysMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.schoolboykeys"))
            .icon(() -> new ItemStack(ModItems.KEYS.get(DyeColor.RED).get()))
            .displayItems((params, output) -> {
                for (DyeColor color : DyeColor.values()) {
                    output.accept(ModItems.KEYS.get(color).get());
                }
                output.accept(ModItems.KEY_DOOR.get());
                output.accept(ModItems.MEDKIT.get());
                output.accept(ModItems.MEDKIT_KEY.get());
                output.accept(ModItems.SLEEPING_PILLS.get());
                output.accept(ModItems.KNIFE.get());
                output.accept(ModItems.FIREWORK.get());
                output.accept(ModItems.FIREWORK_CUT.get());
                output.accept(ModItems.STICK.get());
                output.accept(ModItems.PINK_CUP.get());
            })
            .build());

    private ModTabs() {
    }
}
