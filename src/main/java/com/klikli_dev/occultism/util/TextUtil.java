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

package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.commons.lang3.text.WordUtils;
import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class TextUtil {

    private static final Map<String, String> MOD_NAME_TO_ID = new HashMap<>();

    //KliKli: Obvious :)
    //Xalmas: You know why!
    //Toastbroat: You know why!
    //Ridanisaurus: Pretty things!
    //Najlitarvan: Various contributions & came up with this idea!
    //TheBoo: Ambassador to E6 and many many contributions!
    //Legiaseth: Tried to overload the storage system with nbt. Genius contraption using create to auto-create as much nbt as possible. Love it!
    //Vallen: Did a Bit-By-Bit Video! https://www.youtube.com/watch?v=kAKzzJ_yiC8
    //Vemerion: Sooo many new familiars! <3
    //EqisEdu: the long-awaited additional spirit miner tiers!
    //feellian: New Spirit models!
    private static final String[] EASTER_EGGS = {"KliKli", "Xalmas", "Toastbroat", "Najlitarvan", "TheBoo", "Ridanisaurus", "Legiaseth", "Vallen", "Vemerion", "EqisEdu", "Feellian"};
    private static final String[] SYLLABLE1 = {"Kr", "Ca", "Ra", "Mrok", "Cru", "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol", "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro", "Mar", "Luk", "Bar", "Gor", "Rak", "Thr", "Nar", "Vor", "Fir", "Trin", "Drog", "Karn", "Gar", "Ulf", "Hroth", "Ald", "Yng", "Styr", "Eir", "Ein", "Sig", "Ket", "Erl", "Haf", "Bryn", "Nid", "Grim", "Hol", "Fen", "Sigr", "Geir", "Hyr", "Val", "Har", "Kol", "Eyr"};
    private static final String[] SYLLABLE2 = {"air", "ir", "mi", "sor", "mee", "clo", "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer", "marac", "zoir", "slamar", "salmar", "urak", "tim", "jor", "vyr", "dor", "thor", "kyl", "lyn", "wyn", "wynn", "lond", "rond", "vond", "dorn", "korn", "morn", "gorn", "thorn", "worn", "norn", "rinn", "dell", "bell", "vell", "fell", "kell", "zell", "nir", "fir", "mir", "tir", "sir", "vir", "zir", "lir", "jyr", "ryl", "rym", "lym", "lyn", "ryn", "myr", "myl", "myn", "ryn"};
    private static final String[] SYLLABLE3 = {"d", "ed", "ark", "arc", "es", "er", "der", "tron", "med", "ure", "zur", "cred", "mur", "aeus", "th", "vyr", "dor", "morn", "born", "thorn", "fyr", "lyr", "ryth", "ryn", "drin", "dryn", "kyr", "kyn", "lynd", "lind", "lyne", "line", "ryne", "rine", "thyr", "thyre", "vyn", "vin", "vyne", "vine", "rynne", "rinne", "syr", "syrn", "zirn", "zirne", "kyl", "kylle", "dor", "dorne", "lor", "lorne", "morn", "morne", "thorn", "thorne", "vyrn", "vyrne", "wyrm"};
    private static final Random random = new Random();

    private static boolean modNamesInitialized = false;

    public static void initializeModNames() {
        modNamesInitialized = true;
        for (IModInfo info : ModList.get().getMods()) {
            MOD_NAME_TO_ID.put(info.getModId(), info.getDisplayName());
        }
    }

    /**
     * Gets the mod name for the given game object
     *
     * @param object the game object (item or block) to get the mod name for.
     * @return the mod name or null if invalid object type was supplied.
     */
    @SuppressWarnings("deprecation")
    public static String getModNameForGameObject(@Nonnull Object object) {

        if (modNamesInitialized)
            initializeModNames();

        ResourceLocation key;
        if (object instanceof Item) {
            key = BuiltInRegistries.ITEM.getKey((Item) object);
        } else if (object instanceof Block) {
            key = BuiltInRegistries.BLOCK.getKey((Block) object);
        } else {
            return null;
        }
        String modId = key.getNamespace();
        String lowercaseModId = modId.toLowerCase(Locale.ENGLISH);
        String modName = MOD_NAME_TO_ID.get(lowercaseModId);
        if (modName == null) {
            modName = WordUtils.capitalize(modId);
            MOD_NAME_TO_ID.put(lowercaseModId, modName);
        }
        return modName;
    }

    /**
     * Formats the given spirit name in bold and gold.
     *
     * @param name the name to format.
     * @return the formatted name.
     */
    public static String formatDemonName(String name) {
        return ChatFormatting.GOLD.toString() + ChatFormatting.BOLD + name + ChatFormatting.RESET;
    }

    /**
     * Formats the given spirit name in bold and gold.
     *
     * @param name the name to format.
     * @return the formatted name.
     */
    public static MutableComponent formatDemonName(MutableComponent name) {
        return name.withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD);
    }

    /**
     * Formats the given spirit type name in a color based on the type.
     */
    public static MutableComponent formatDemonType(Component name, EntityType<?> type) {
        var egg = SpawnEggItem.byId(type);
        var color = egg != null ? egg.getColor(0) : 0xffffff;
        color = makeColorLighterForDarkMode(color);
        int finalColor = color;
        return Component.empty().append(name).withStyle(style -> style.withColor(finalColor));
    }

    //I'll admit I just had gpt 4 whip this up, 0 extra effort to avoid AWT - love it.
    private static int makeColorLighterForDarkMode(int colorCode) {
        final float BRIGHTNESS_THRESHOLD = 0.4f;
        final float BRIGHTNESS_INCREASE = 0.4f;

        int r = (colorCode >> 16) & 0xFF;
        int g = (colorCode >> 8) & 0xFF;
        int b = colorCode & 0xFF;

        float[] hsb = rgbToHsb(r, g, b);

        if (hsb[2] < BRIGHTNESS_THRESHOLD) {
            hsb[2] += BRIGHTNESS_INCREASE;
            hsb[2] = Math.min(hsb[2], 1.0f);

            int[] rgb = hsbToRgb(hsb[0], hsb[1], hsb[2]);
            colorCode = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        }

        return colorCode;
    }

    private static float[] rgbToHsb(int r, int g, int b) {
        float rf = r / 255.0f;
        float gf = g / 255.0f;
        float bf = b / 255.0f;

        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float delta = max - min;

        float h = 0.0f;
        float s = max == 0.0 ? 0.0f : delta / max;
        float v = max;

        if (delta != 0.0f) {
            if (max == rf) {
                h = (gf - bf) / 6 / delta;
            } else if (max == gf) {
                h = (1.0f / 3) + (bf - rf) / 6 / delta;
            } else {
                h = (2.0f / 3) + (rf - gf) / 6 / delta;
            }

            if (h < 0) {
                h += 1;
            }
        }

        return new float[]{h, s, v};
    }

    private static int[] hsbToRgb(float h, float s, float v) {
        int r = 0, g = 0, b = 0;

        int i = (int) (h * 6);
        float f = h * 6 - i;
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);

        switch (i % 6) {
            case 0:
                r = Math.round(v * 255);
                g = Math.round(t * 255);
                b = Math.round(p * 255);
                break;
            case 1:
                r = Math.round(q * 255);
                g = Math.round(v * 255);
                b = Math.round(p * 255);
                break;
            case 2:
                r = Math.round(p * 255);
                g = Math.round(v * 255);
                b = Math.round(t * 255);
                break;
            case 3:
                r = Math.round(p * 255);
                g = Math.round(q * 255);
                b = Math.round(v * 255);
                break;
            case 4:
                r = Math.round(t * 255);
                g = Math.round(p * 255);
                b = Math.round(v * 255);
                break;
            case 5:
                r = Math.round(v * 255);
                g = Math.round(p * 255);
                b = Math.round(q * 255);
                break;
        }

        return new int[]{r, g, b};
    }

    /**
     * Formats the given number for human friendly display. Rounds high numbers.
     *
     * @param number the number to format.
     * @return a formatted string for the number.
     */
    public static String formatLargeNumber(int number) {
        if (number < Math.pow(10, 3)) {
            return String.valueOf(number);
        } else if (number < Math.pow(10, 6)) {
            int rounded = Math.round(number / 1000.0F);
            return rounded + "K";
        } else if (number < Math.pow(10, 9)) {
            int rounded = Math.round(number / (float) Math.pow(10, 6));
            return rounded + "M";
        } else if (number < Math.pow(10, 12)) {
            int rounded = Math.round(number / (float) Math.pow(10, 9));
            return rounded + "B";
        }
        return Integer.toString(number);
    }

    /**
     * @return a random name from the 3 syllable variations.
     */
    public static String generateName() {
        var possibleSpiritNames = Occultism.STARTUP_CONFIG.rituals.possibleSpiritNames.get();
        var usePossibleSpiritNames = random.nextDouble() > Occultism.STARTUP_CONFIG.rituals.usePossibleSpiritNamesChance.get();

        //if possible spirit names is not empty, and the random chance is met we use a name from the list
        if (!possibleSpiritNames.isEmpty() && usePossibleSpiritNames) {
            return random.nextInt(20) == 0 ?
                    EASTER_EGGS[random.nextInt(EASTER_EGGS.length)] :
                    possibleSpiritNames.get(random.nextInt(possibleSpiritNames.size()));
        }

        //otherwise we go with the default behaviour, which is random names
        return random.nextInt(20) == 0 ? EASTER_EGGS[random.nextInt(
                EASTER_EGGS.length)] : SYLLABLE1[random.nextInt(SYLLABLE1.length)] + SYLLABLE2[random.nextInt(SYLLABLE2.length)] +
                SYLLABLE3[random.nextInt(SYLLABLE3.length)];
    }
}
