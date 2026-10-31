package net.schoolboykeys.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.schoolboykeys.KeysMod;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KeysMod.MODID);

    public static final RegistryObject<SoundEvent> KEY_PICKUP = register("key_pickup");
    public static final RegistryObject<SoundEvent> DOOR_LOCKED = register("door_locked");
    public static final RegistryObject<SoundEvent> DOOR_UNLOCK = register("door_unlock");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(KeysMod.MODID, name)));
    }

    private ModSounds() {
    }
}
