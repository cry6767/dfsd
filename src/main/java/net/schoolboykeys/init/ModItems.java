package net.schoolboykeys.init;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.schoolboykeys.KeysMod;
import net.schoolboykeys.item.ColorKeyItem;
import net.schoolboykeys.item.KnifeItem;
import net.schoolboykeys.item.MedkitKeyItem;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KeysMod.MODID);

    public static final Map<DyeColor, RegistryObject<Item>> KEYS = new EnumMap<>(DyeColor.class);
    public static final RegistryObject<Item> KEY_DOOR;
    public static final RegistryObject<Item> MEDKIT;
    public static final RegistryObject<Item> MEDKIT_KEY;
    public static final RegistryObject<Item> SLEEPING_PILLS;
    public static final RegistryObject<Item> KNIFE;
    public static final RegistryObject<Item> FIREWORK;
    public static final RegistryObject<Item> FIREWORK_CUT;
    public static final RegistryObject<Item> STICK;
    public static final RegistryObject<Item> PINK_CUP;

    static {
        for (DyeColor color : DyeColor.values()) {
            KEYS.put(color, ITEMS.register("key_" + color.getName(),
                    () -> new ColorKeyItem(ModBlocks.KEY_BLOCKS.get(color).get(), new Item.Properties().stacksTo(1))));
        }
        KEY_DOOR = ITEMS.register("key_door",
                () -> new DoubleHighBlockItem(ModBlocks.KEY_DOOR.get(), new Item.Properties()));
        MEDKIT = ITEMS.register("medkit", () -> new BlockItem(ModBlocks.MEDKIT.get(), new Item.Properties()));
        MEDKIT_KEY = ITEMS.register("medkit_key", () -> new MedkitKeyItem(new Item.Properties().stacksTo(1)));
        KNIFE = ITEMS.register("knife", () -> new KnifeItem(ModBlocks.KNIFE.get(), new Item.Properties().stacksTo(1)));
        FIREWORK = ITEMS.register("firework", () -> new BlockItem(ModBlocks.FIREWORK.get(), new Item.Properties().stacksTo(16)));
        FIREWORK_CUT = ITEMS.register("firework_cut", () -> new BlockItem(ModBlocks.FIREWORK_CUT.get(), new Item.Properties().stacksTo(16)));
        STICK = ITEMS.register("stick", () -> new BlockItem(ModBlocks.STICK.get(), new Item.Properties()));
        PINK_CUP = ITEMS.register("pink_cup", () -> new BlockItem(ModBlocks.PINK_CUP.get(), new Item.Properties()));
        SLEEPING_PILLS = ITEMS.register("sleeping_pills", () -> new BlockItem(ModBlocks.SLEEPING_PILLS.get(), new Item.Properties().stacksTo(16)));
    }

    private ModItems() {
    }
}
