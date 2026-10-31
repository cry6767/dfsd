package net.schoolboykeys.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.schoolboykeys.KeysMod;

public final class ModTags {
    /** Таблетки, которые можно засыпать в аптечку (слабительное из SchoolBoyRunaway и снотворное). */
    public static final TagKey<Item> PILLS = TagKey.create(Registries.ITEM, new ResourceLocation(KeysMod.MODID, "pills"));

    private ModTags() {
    }
}
