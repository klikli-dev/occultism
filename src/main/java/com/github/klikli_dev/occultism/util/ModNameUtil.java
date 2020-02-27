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

package com.github.klikli_dev.occultism.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ModNameUtil {
    //region Fields
    private static final Map<String, String> MOD_NAME_TO_ID = new HashMap<String, String>();
    //endregion Fields

    //region Static Methods
    public static void postInit() {
        Map<String, ModContainer> modList = Loader.instance().getIndexedModList();
        for (Map.Entry<String, ModContainer> modEntry : modList.entrySet()) {
            String lowercaseId = modEntry.getKey().toLowerCase(Locale.ENGLISH);
            String modName = modEntry.getValue().getName();
            MOD_NAME_TO_ID.put(lowercaseId, modName);
        }
    }

    /**
     * Gets the mod name for the given game object
     *
     * @param object the game object (item or block) to get the mod name for.
     * @return the mod name or null if invalid object type was supplied.
     */
    public static String getModNameForGameObject(@Nonnull Object object) {
        ResourceLocation itemResourceLocation;
        if (object instanceof Item) {
            itemResourceLocation = Item.REGISTRY.getNameForObject((Item) object);
        }
        else if (object instanceof Block) {
            itemResourceLocation = Block.REGISTRY.getNameForObject((Block) object);
        }
        else {
            return null;
        }
        String modId = itemResourceLocation.getNamespace();
        String lowercaseModId = modId.toLowerCase(Locale.ENGLISH);
        String modName = MOD_NAME_TO_ID.get(lowercaseModId);
        if (modName == null) {
            modName = WordUtils.capitalize(modId);
            MOD_NAME_TO_ID.put(lowercaseModId, modName);
        }
        return modName;
    }
    //endregion Static Methods
}
