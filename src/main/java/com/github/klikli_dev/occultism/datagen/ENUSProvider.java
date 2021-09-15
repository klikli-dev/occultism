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
        addAdvancements();
        

    }
    
    private void addAdvancements() {
        advancementTitle("root", "Occultism: Friends");
        advancementDescr("root", "Use a ritual to summon a familiar");
        
        advancementTitle("deer", "Demonic Poop");
        advancementDescr("deer", "Observe when your deer familiar poops demon seed");
        
        advancementTitle("cthulhu", "You Monster!");
        advancementDescr("cthulhu", "Make your cthulhu familiar sad");
        
        advancementTitle("bat", "Cannibalism");
        advancementDescr("bat", "Lure a normal bat near your bat familiar");
        
        advancementTitle("devil", "Hellfire");
        advancementDescr("devil", "Command your devil familiar to breath fire");
        
        advancementTitle("greedy", "Errand Boy");
        advancementDescr("greedy", "Let your greedy familiar pick something up for you");
        
        advancementTitle("rare", "Rare Friend");
        advancementDescr("rare", "Obtain a rare familiar variant");
        
        advancementTitle("party", "Dance!");
        advancementDescr("party", "Get your familiar to dance");
        
        advancementTitle("capture", "Catch them all!");
        advancementDescr("capture", "Trap your familiar in a familiar ring");
    }

    private void advancementTitle(String name, String s) {
        add(OccultismAdvancementProvider.title(name).getKey(), s);
    }
    
    private void advancementDescr(String name, String s) {
        add(OccultismAdvancementProvider.descr(name).getKey(), s);

    }

}
