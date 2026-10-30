package net.schoolboykeys;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.schoolboykeys.init.ModBlockEntities;
import net.schoolboykeys.init.ModBlocks;
import net.schoolboykeys.init.ModItems;
import net.schoolboykeys.init.ModTabs;

@Mod(KeysMod.MODID)
public class KeysMod {
    public static final String MODID = "schoolboykeys";

    public KeysMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
    }
}
