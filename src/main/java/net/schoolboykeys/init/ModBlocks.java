package net.schoolboykeys.init;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.schoolboykeys.KeysMod;
import net.schoolboykeys.block.KeyBlock;
import net.schoolboykeys.block.KeyDoorBlock;
import net.schoolboykeys.block.MedkitBlock;
import net.schoolboykeys.block.FireworkBlock;
import net.schoolboykeys.block.PillsBlock;
import net.schoolboykeys.block.PinkCupBlock;
import net.schoolboykeys.block.PropBlock;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KeysMod.MODID);

    public static final Map<DyeColor, RegistryObject<Block>> KEY_BLOCKS = new EnumMap<>(DyeColor.class);
    public static final RegistryObject<Block> KEY_DOOR;
    public static final RegistryObject<Block> MEDKIT;
    public static final RegistryObject<Block> SLEEPING_PILLS;
    public static final RegistryObject<Block> KNIFE;
    public static final RegistryObject<Block> FIREWORK;
    public static final RegistryObject<Block> FIREWORK_CUT;
    public static final RegistryObject<Block> STICK;
    public static final RegistryObject<Block> PINK_CUP;

    static {
        for (DyeColor color : DyeColor.values()) {
            KEY_BLOCKS.put(color, BLOCKS.register("key_" + color.getName(), KeyBlock::new));
        }
        KEY_DOOR = BLOCKS.register("key_door", KeyDoorBlock::new);
        MEDKIT = BLOCKS.register("medkit", MedkitBlock::new);
        SLEEPING_PILLS = BLOCKS.register("sleeping_pills", PillsBlock::new);
        KNIFE = BLOCKS.register("knife", () -> new PropBlock(14.5D, 3.5D, 2.3D));
        FIREWORK = BLOCKS.register("firework", FireworkBlock::new);
        FIREWORK_CUT = BLOCKS.register("firework_cut", () -> new PropBlock(13.0D, 6.0D, 2.0D));
        STICK = BLOCKS.register("stick", () -> new PropBlock(13.6D, 5.0D, 1.6D));
        PINK_CUP = BLOCKS.register("pink_cup", PinkCupBlock::new);
    }

    private ModBlocks() {
    }
}
