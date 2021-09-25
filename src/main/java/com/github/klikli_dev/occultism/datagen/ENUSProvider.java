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

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ENUSProvider extends LanguageProvider {

    public ENUSProvider(DataGenerator gen) {
        super(gen, Occultism.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addAdvancements();
        

    }
    
    private void addAdvancements() {
        this.advancementTitle("root", "Occultism: Friends");
        this.advancementDescr("root", "Use a ritual to summon a familiar");

        this.advancementTitle("deer", "Demonic Poop");
        this.advancementDescr("deer", "Observe when your deer familiar poops demon seed");

        this.advancementTitle("cthulhu", "You Monster!");
        this.advancementDescr("cthulhu", "Make your cthulhu familiar sad");

        this.advancementTitle("bat", "Cannibalism");
        this.advancementDescr("bat", "Lure a normal bat near your bat familiar");

        this.advancementTitle("devil", "Hellfire");
        this.advancementDescr("devil", "Command your devil familiar to breath fire");

        this.advancementTitle("greedy", "Errand Boy");
        this.advancementDescr("greedy", "Let your greedy familiar pick something up for you");

        this.advancementTitle("rare", "Rare Friend");
        this.advancementDescr("rare", "Obtain a rare familiar variant");

        this.advancementTitle("party", "Dance!");
        this.advancementDescr("party", "Get your familiar to dance");

        this.advancementTitle("capture", "Catch them all!");
        this.advancementDescr("capture", "Trap your familiar in a familiar ring");
    }

    private void advancementTitle(String name, String s) {
        this.add(OccultismAdvancementProvider.title(name).getKey(), s);
    }
    
    private void advancementDescr(String name, String s) {
        this.add(OccultismAdvancementProvider.descr(name).getKey(), s);

    }

}
