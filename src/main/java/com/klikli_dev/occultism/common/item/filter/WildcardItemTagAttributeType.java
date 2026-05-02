/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.common.item.filter;

import com.klikli_dev.codedefinedgui.filter.attribute.AttributeCandidate;
import com.klikli_dev.codedefinedgui.filter.attribute.AttributeRule;
import com.klikli_dev.codedefinedgui.filter.attribute.ItemAttributeType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class WildcardItemTagAttributeType implements ItemAttributeType {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("occultism", "item_tag_pattern");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public List<AttributeCandidate> collectCandidates(ItemStack reference, Level level) {
        return List.of();
    }

    @Override
    public boolean matches(ItemStack candidate, Level level, CustomData payload) {
        String pattern = payload.copyTag().getString("pattern").orElse("");
        return !pattern.isBlank() && candidate.tags().anyMatch(tag -> FilenameUtils.wildcardMatch(tag.location().toString(), pattern, IOCase.INSENSITIVE));
    }

    @Override
    public Component describe(CustomData payload, HolderLookup.Provider registries, boolean inverted) {
        String pattern = payload.copyTag().getString("pattern").orElse("");
        return Component.literal((inverted ? "NOT #" : "#") + pattern);
    }

    public static AttributeRule rule(String pattern) {
        CompoundTag tag = new CompoundTag();
        tag.putString("pattern", pattern);
        return new AttributeRule(ID, CustomData.of(tag), false);
    }
}
