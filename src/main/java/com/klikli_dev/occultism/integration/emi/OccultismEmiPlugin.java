package com.klikli_dev.occultism.integration.emi;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.klikli_dev.occultism.crafting.recipe.CrushingRecipe;
import com.klikli_dev.occultism.crafting.recipe.MinerRecipe;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.integration.emi.recipes.CrushingRecipeCategory;
import com.klikli_dev.occultism.integration.emi.recipes.MinerRecipeCategory;
import com.klikli_dev.occultism.integration.emi.recipes.RitualRecipeCategory;
import com.klikli_dev.occultism.integration.emi.recipes.SpiritFireRecipeCategory;
import com.klikli_dev.occultism.integration.emi.render.SpiritRenderable;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@EmiEntrypoint
public class OccultismEmiPlugin implements EmiPlugin {
    public static final EmiStack SPIRIT_FIRE = EmiStack.of(OccultismItems.SPIRIT_FIRE.get());
    public static final EmiStack DIMENSIONAL_MINESHAFT = EmiStack.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get());
    public static final EmiStack GOLDEN_SACRIFICIAL_BOWL = EmiStack.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get());
    public static final ResourceLocation EMI_WIDGETS = new ResourceLocation(Occultism.MODID, "textures/gui/emi/widgets.png");
    public static final EmiRecipeCategory SPIRIT_FIRE_CATEGORY = new EmiRecipeCategory(new ResourceLocation(Occultism.MODID, "spirit_fire"),SPIRIT_FIRE, new EmiTexture(EMI_WIDGETS, 0, 0, 16, 16));
    public static final EmiRecipeCategory CRUSHING_CATEGORY = new EmiRecipeCategory(new ResourceLocation(Occultism.MODID, "crushing"), new SpiritRenderable<FoliotEntity>(OccultismEntities.FOLIOT.get()), new EmiTexture(EMI_WIDGETS, 16, 0, 16, 16));
    public static final EmiRecipeCategory MINER_CATEGORY = new EmiRecipeCategory(new ResourceLocation(Occultism.MODID, "miner"), DIMENSIONAL_MINESHAFT, new EmiTexture(EMI_WIDGETS, 32, 0, 16, 16));
    public static final EmiRecipeCategory RITUAL_CATEGORY = new EmiRecipeCategory(new ResourceLocation(Occultism.MODID, "ritual"),GOLDEN_SACRIFICIAL_BOWL , new EmiTexture(EMI_WIDGETS, 48, 0, 16, 16));

    @Override
    public void initialize(EmiInitRegistry registry) {
        EmiPlugin.super.initialize(registry);
    }

    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addCategory(SPIRIT_FIRE_CATEGORY);
        emiRegistry.addWorkstation(SPIRIT_FIRE_CATEGORY, SPIRIT_FIRE);
        emiRegistry.addCategory(CRUSHING_CATEGORY);
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Occultism.MODID, "ritual_dummy/summon_foliot_crusher"))))));
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Occultism.MODID, "ritual_dummy/summon_djinni_crusher"))))));
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Occultism.MODID, "ritual_dummy/summon_afrit_crusher"))))));
        emiRegistry.addWorkstation(CRUSHING_CATEGORY, EmiStack.of(new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Occultism.MODID, "ritual_dummy/summon_marid_crusher"))))));

        emiRegistry.addCategory(MINER_CATEGORY);
        emiRegistry.addWorkstation(MINER_CATEGORY, EmiStack.of(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()));

        emiRegistry.addCategory(RITUAL_CATEGORY);
        emiRegistry.addWorkstation(RITUAL_CATEGORY, GOLDEN_SACRIFICIAL_BOWL);
        RecipeManager manager=emiRegistry.getRecipeManager();
        for(SpiritFireRecipe recipe: manager.getAllRecipesFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get())) {
            emiRegistry.addRecipe(new SpiritFireRecipeCategory(recipe));
        }
        for(CrushingRecipe recipe:manager.getAllRecipesFor(OccultismRecipes.CRUSHING_TYPE.get())){
            emiRegistry.addRecipe(new CrushingRecipeCategory(recipe));
        }
        for(MinerRecipe recipe:manager.getAllRecipesFor(OccultismRecipes.MINER_TYPE.get())){
            emiRegistry.addRecipe(new MinerRecipeCategory(recipe));
        }
        for(RitualRecipe recipe:manager.getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get())){
            emiRegistry.addRecipe(new RitualRecipeCategory(recipe));
        }
    }
}
