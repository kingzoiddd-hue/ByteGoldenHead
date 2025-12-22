package dev.zoid.bytegoldenhead.item;

import com.destroystokyo.paper.profile.ProfileProperty;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.FoodProperties;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import io.papermc.paper.datacomponent.item.UseCooldown;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import java.util.List;
import java.util.UUID;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public final class GoldenHead {
    public static final NamespacedKey ITEM_KEY = new NamespacedKey("byte", "golden_head");
    private static final String TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTcyNjIzMzgyODUxMiwKICAicHJvZmlsZUlkIiA6ICJhYzY1NDYwOWVkZjM0ODhmOTM0ZWNhMDRmNjlkNGIwMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJzcGFjZUd1cmxTa3kiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUxNTUxYjk0MzRmYWUxZThlOTMyN2Y3YzFjZWFjN2JjYWUzY2Y3MWZiZDY3ZTc5NDJmMGMyY2U2MjgwNWMxOCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";

    private static ItemStack cachedItem;

    private GoldenHead() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void init() {
        if (cachedItem != null) {
            return;
        }

        final ItemStack head = new ItemStack(Material.HONEY_BOTTLE);

        head.setData(DataComponentTypes.PROFILE, ResolvableProfile.resolvableProfile()
                .uuid(UUID.randomUUID())
                .addProperty(new ProfileProperty("textures", TEXTURE))
                .build());

        head.setData(DataComponentTypes.ITEM_MODEL, Key.key("minecraft:player_head"));
        head.setData(DataComponentTypes.ITEM_NAME, Component.text("Golden Head"));
        head.setData(DataComponentTypes.RARITY, ItemRarity.RARE);

        head.setData(DataComponentTypes.FOOD, FoodProperties.food()
                .nutrition(4)
                .saturation(9.6f)
                .canAlwaysEat(true)
                .build());

        head.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable()
                .animation(ItemUseAnimation.EAT)
                .sound(Key.key("entity.generic.eat"))
                .hasConsumeParticles(true)
                .addEffect(ConsumeEffect.applyStatusEffects(
                        List.of(
                                new PotionEffect(PotionEffectType.REGENERATION, 100, 2),
                                new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0)
                        ),
                        1.0f))
                .build());

        head.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(10.0f).build());
        head.unsetData(DataComponentTypes.BLOCK_DATA);

        final ItemMeta meta = head.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(ITEM_KEY, PersistentDataType.BOOLEAN, true);
            head.setItemMeta(meta);
        }

        cachedItem = head;
    }

    public static @NotNull ItemStack getItem() {
        if (cachedItem == null) {
            init();
        }
        return cachedItem.clone();
    }
}