package com.p1nero.tcrcore.datagen;

import com.p1nero.tcrcore.datagen.tags.TCRItemTags;
import com.p1nero.tcrcore.item.TCRItems;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class TCRRecipeGenerator extends TCRRecipeProvider implements IConditionBuilder {
    public TCRRecipeGenerator(PackOutput output) {
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCRItems.WITHER_SOUL_STONE_ACTIVATED.get(), 1)
                .requires(TCRItems.WITHER_SOUL_STONE.get())
                .requires(Items.GHAST_TEAR)
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1)
                .requires(TCRItemTags.CATACLYSM_HUMANOID_BOSS_DROP)
                .requires(TCRItemTags.CATACLYSM_HUMANOID_BOSS_DROP)
                .requires(TCRItemTags.CATACLYSM_HUMANOID_BOSS_DROP)
                .requires(TCRItemTags.CATACLYSM_HUMANOID_BOSS_DROP)
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCRItems.PROOF_OF_ADVENTURE_PLUS.get(), 1)
                .requires(TCRItems.ABYSS_FRAGMENT.get())
                .requires(TCRItems.STORM_FRAGMENT.get())
                .requires(TCRItems.FLAME_FRAGMENT.get())
                .requires(TCRItems.MECH_FRAGMENT.get())
                .requires(TCRItems.SOUL_FRAGMENT.get())
                .requires(TCRItems.DESERT_FRAGMENT.get())
                .requires(TCRItems.ENDER_FRAGMENT.get())
                .requires(TCRItems.NETHERITE_FRAGMENT.get())
                .requires(TCRItems.DIVINE_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ICE_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.SOUL_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BLOOD_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.PROOF_OF_ADVENTURE.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FIRE_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.FLAME_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.PROTECTION_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.NETHERITE_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.COOLDOWN_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.RARE_ARTIFACT_TICKET.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ENDER_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.ENDER_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.NATURE_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.DESERT_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.LIGHTNING_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.STORM_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MANA_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.ABYSS_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.EVOCATION_UPGRADE_ORB.get(), 1)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(TCRItems.MECH_FRAGMENT.get())
                .unlockedBy(getHasName(TCRItems.WITHER_SOUL_STONE.get()), has(TCRItems.WITHER_SOUL_STONE.get()))
                .save(consumer);
    }

}
