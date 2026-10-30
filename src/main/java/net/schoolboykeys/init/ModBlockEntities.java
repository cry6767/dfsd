package net.schoolboykeys.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.schoolboykeys.KeysMod;
import net.schoolboykeys.block.KeyBlockEntity;
import net.schoolboykeys.block.KeyDoorBlockEntity;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KeysMod.MODID);

    public static final RegistryObject<BlockEntityType<KeyBlockEntity>> KEY_BLOCK = BLOCK_ENTITIES.register("key_block",
            () -> BlockEntityType.Builder.of(KeyBlockEntity::new,
                    ModBlocks.KEY_BLOCKS.values().stream().map(RegistryObject::get).toArray(Block[]::new)).build(null));

    public static final RegistryObject<BlockEntityType<KeyDoorBlockEntity>> KEY_DOOR = BLOCK_ENTITIES.register("key_door",
            () -> BlockEntityType.Builder.of(KeyDoorBlockEntity::new, ModBlocks.KEY_DOOR.get()).build(null));

    private ModBlockEntities() {
    }
}
