/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.integration.jei;

import com.github.klikli_dev.occultism.common.container.ContainerStorageController;
import com.github.klikli_dev.occultism.crafting.recipe.RecipeSpiritfireConversion;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import com.google.common.base.Strings;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    //region Fields
    protected static boolean jeiLoaded;
    protected static boolean jeiSearchSync = true;
    protected static IJeiRuntime runtime;
    //endregion Fields

    //region Getter / Setter
    public static boolean isJeiLoaded() {
        return jeiLoaded;
    }

    public static void setJeiLoaded(boolean loaded) {
        JeiPlugin.jeiLoaded = loaded;
    }

    public static boolean isJeiSearchSynced() {
        return jeiSearchSync;
    }

    public static IJeiRuntime getJeiRuntime() {
        return runtime;
    }

    public static String getFilterText() {
        if (runtime != null)
            return runtime.getIngredientFilter().getFilterText();
        return "";
    }

    public static void setFilterText(String filter) {
        if (runtime != null)
            runtime.getIngredientFilter().setFilterText(Strings.nullToEmpty(filter));
    }

    public static void setJeiSearchSync(boolean synced) {
        JeiPlugin.jeiSearchSync = synced;
    }
    //endregion Getter / Setter

    //region Overrides
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new SpiritFireRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.getRecipeTransferRegistry().addUniversalRecipeTransferHandler(
                new StorageControllerRecipeTransferHandler<>(ContainerStorageController.class));

        registry.handleRecipes(RecipeSpiritfireConversion.class, SpiritFireRecipeWrapper::new,
                SpiritFireRecipeCategory.UID);
        registry.addRecipes(BlockRegistry.SPIRIT_FIRE.recipes, SpiritFireRecipeCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.SPIRIT_FIRE), SpiritFireRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiPlugin.runtime = jeiRuntime;
    }
    //endregion Overrides

    //region Static Methods
    public static void preInit() {
        jeiLoaded = Loader.isModLoaded("jei");
    }
    //endregion Static Methods
}
