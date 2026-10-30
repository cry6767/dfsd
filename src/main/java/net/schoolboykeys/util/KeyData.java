package net.schoolboykeys.util;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

/** Работа с уникальным номером ключа, который хранится в NBT предмета. */
public final class KeyData {
    public static final String TAG = "KeyId";

    private KeyData() {
    }

    /** Номер ключа или null, если ключ ещё ни к чему не привязан. */
    public static String get(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG, Tag.TAG_STRING)) {
            String id = tag.getString(TAG);
            return id.isEmpty() ? null : id;
        }
        return null;
    }

    /** Возвращает номер ключа, при необходимости создаёт новый. */
    public static String ensure(ItemStack stack) {
        String id = get(stack);
        if (id == null) {
            id = UUID.randomUUID().toString();
            stack.getOrCreateTag().putString(TAG, id);
        }
        return id;
    }

    public static void set(ItemStack stack, String id) {
        stack.getOrCreateTag().putString(TAG, id);
    }

    public static String shortId(String id) {
        return id.length() > 6 ? id.substring(0, 6).toUpperCase() : id.toUpperCase();
    }
}
