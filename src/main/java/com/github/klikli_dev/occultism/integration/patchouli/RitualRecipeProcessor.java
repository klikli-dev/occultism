/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.github.klikli_dev.occultism.integration.patchouli;


import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class RitualRecipeProcessor implements IComponentProcessor {

    protected RitualRecipe recipe;
    protected ItemStack sacrificialBowl;

    @Override
    public void setup(IVariableProvider iVariableProvider) {
        String recipeId = iVariableProvider.get("recipe").asString();
        this.recipe = (RitualRecipe) Minecraft.getInstance().level.getRecipeManager()
                .byKey(new ResourceLocation(recipeId)).orElse(null);
        this.sacrificialBowl = new ItemStack(OccultismBlocks.SACRIFICIAL_BOWL.get());
    }

    @Override
    public IVariable process(String key) {
        //TODO: patchouli recipe processor process never called
        if (this.recipe == null)
            return IVariable.empty(); // if recipe was not found (e.g. due to modpack changes) exit early

        if (key.startsWith("ritual_dummy")) {
            return IVariable.from(this.recipe.getRitualDummy());
        }

        if (key.startsWith("activation_item")) {
            return IVariable.from(this.recipe.getActivationItem().getItems());
        }

        if (key.startsWith("pentacle")) {
            if (this.recipe.getPentacle() != null) {
                //$(l:pentacles/summon_foliot)Aviar's Circle$(/l)
                String pentacleName = I18n.get(this.recipe.getPentacle().getDescriptionId());
                String pentacleLink = "pentacles/" + this.recipe.getPentacleId().getPath();
                return IVariable.wrap(String.format("$(l:%s)%s$(/l)", pentacleLink, pentacleName));
            }
        }

        //fill ingredient variables
        if (key.startsWith("ingredient")) {
            //only use ingredient slots we actually have ingredients for
            int index = Integer.parseInt(key.substring("ingredient".length())) - 1;
            if (index >= this.recipe.getIngredients().size())
                return IVariable.empty();


            Ingredient ingredient = this.recipe.getIngredients().get(index);
            return IVariable.from(ingredient.getItems());
        }

        //fill sacrificial bowl variables depending on required ingredients
        if (key.startsWith("bowl")) {
            //only use bowl slots we actually have ingredients for
            int index = Integer.parseInt(key.substring("bowl".length())) - 1;
            if (index >= this.recipe.getIngredients().size())
                return IVariable.empty();

            return IVariable.from(this.sacrificialBowl);
        }

        //Recipe crafting output
        if (key.equals("output")) {
            //do not show none dummy -> instead show ritual dummy again
            if (this.recipe.getResultItem().getItem() != OccultismItems.JEI_DUMMY_NONE.get()) {
                //if we have an item output -> render it
                return IVariable.from(this.recipe.getResultItem());
            } else {
                //if not, we instead render our ritual dummy item, just like in the corner
                return IVariable.from(this.recipe.getRitualDummy());
            }
        }

        if (key.equals("entity_to_summon")) {
            if (this.recipe.getEntityToSummon() != null) {
                return IVariable.wrap(I18n.get("jei.occultism.summon", I18n.get(this.recipe.getEntityToSummon().getDescriptionId())));
            }
        }

        if (key.equals("job")) {
            if (this.recipe.getSpiritJobType() != null) {
                return IVariable.wrap(I18n.get("jei.occultism.job", I18n.get("job." + this.recipe.getSpiritJobType().toString().replace(":", "."))));
            }
        }

        if (key.equals("entity_to_sacrifice")) {
            if (this.recipe.requiresSacrifice()) {
                return IVariable.wrap(I18n.get("jei.occultism.sacrifice", I18n.get(this.recipe.getEntityToSacrificeDisplayName())));
            }
        }

        if (key.equals("item_to_use")) {
            if (this.recipe.requiresItemUse())
                return IVariable.from(this.recipe.getItemToUse().getItems());
        }

        if (key.equals("item_to_use_text")) {
            if (this.recipe.requiresItemUse()) {
                return IVariable.wrap(I18n.get("jei.occultism.item_to_use"));
            }
        }

        return IVariable.empty();
    }
}
