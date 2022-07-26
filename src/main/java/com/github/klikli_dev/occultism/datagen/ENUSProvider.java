/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.common.data.LanguageProvider;

public class ENUSProvider extends LanguageProvider {

    public ENUSProvider(DataGenerator gen) {
        super(gen, Occultism.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addAdvancements();
        this.addBook();

        //TODO: pentacle names Util.makeDescriptionId("multiblock", pentacle.getId()))
    }

    private void addBook(){
        var helper = ModonomiconAPI.get().getLangHelper(Modonomicon.MODID);
        helper.book("dictionary_of_spirits");

//        this.addDemoBookFeaturesCategory(helper);
//        this.addDemoBookFormattingCategory(helper);

        this.add(helper.bookName(), "Dictionary of Spirits");
        this.add(helper.bookTooltip(), """
                This book aims to introduce the novice reader to the most common summoning rituals and equip them with a list of spirits and their names. 
                The authors advise caution in the summoning of the listed entities. 
                For help or to give feedback please join us in Discord https://invite.gg/klikli.
                """);
    }

    private void addAdvancements() {
        this.advancementTitle("root", "Occultism");
        this.advancementDescr("root", "Get spiritual");

        this.advancementTitle("familiars", "Occultism: Friends");
        this.advancementDescr("familiars", "Use a ritual to summon a familiar");

        this.familiarAdvancementTitle("deer", "Demonic Poop");
        this.familiarAdvancementDescr("deer", "Observe when your deer familiar poops demon seed");

        this.familiarAdvancementTitle("cthulhu", "You Monster!");
        this.familiarAdvancementDescr("cthulhu", "Make your cthulhu familiar sad");

        this.familiarAdvancementTitle("bat", "Cannibalism");
        this.familiarAdvancementDescr("bat", "Lure a normal bat near your bat familiar");

        this.familiarAdvancementTitle("devil", "Hellfire");
        this.familiarAdvancementDescr("devil", "Command your devil familiar to breath fire");

        this.familiarAdvancementTitle("greedy", "Errand Boy");
        this.familiarAdvancementDescr("greedy", "Let your greedy familiar pick something up for you");

        this.familiarAdvancementTitle("rare", "Rare Friend");
        this.familiarAdvancementDescr("rare", "Obtain a rare familiar variant");

        this.familiarAdvancementTitle("party", "Dance!");
        this.familiarAdvancementDescr("party", "Get your familiar to dance");

        this.familiarAdvancementTitle("capture", "Catch them all!");
        this.familiarAdvancementDescr("capture", "Trap your familiar in a familiar ring");

        this.familiarAdvancementTitle("dragon_nugget", "Deal!");
        this.familiarAdvancementDescr("dragon_nugget", "Give a gold nugget to your dragon familiar");

        this.familiarAdvancementTitle("dragon_ride", "Working together");
        this.familiarAdvancementDescr("dragon_ride", "Let your greedy familiar pick something up while riding a dragon familiar");
    }

    private void familiarAdvancementTitle(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.familiarTitle(name).getContents()).getKey(), s);
    }

    private void familiarAdvancementDescr(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.familiarDescr(name).getContents()).getKey(), s);
    }

    private void advancementTitle(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.title(name).getContents()).getKey(), s);
    }

    private void advancementDescr(String name, String s) {
        this.add(((TranslatableContents)OccultismAdvancementProvider.descr(name).getContents()).getKey(), s);
    }
}
