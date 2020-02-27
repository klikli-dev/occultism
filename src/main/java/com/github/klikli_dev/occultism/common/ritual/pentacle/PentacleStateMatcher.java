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

package com.github.klikli_dev.occultism.common.ritual.pentacle;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.block.BlockChalkGlyph;
import net.minecraft.block.BlockState;
import net.minecraft.block.properties.IProperty;
import vazkii.patchouli.api.IStateMatcher;

import java.util.function.Predicate;

/**
 * Custom state matcher for patchouli_books multiblocks
 */
public class PentacleStateMatcher implements IStateMatcher {

    //region Fields
    private final BlockState displayState;
    private final Predicate<BlockState> statePredicate;
    //endregion Fields

    //region Initialization
    private PentacleStateMatcher(BlockState displayState, Predicate<BlockState> statePredicate) {
        this.displayState = displayState;
        this.statePredicate = statePredicate;
    }
    //endregion Initialization

    //region Overrides
    @Override
    public BlockState getDisplayedState() {
        return this.displayState;
    }

    @Override
    public Predicate<BlockState> getStatePredicate() {
        return this.statePredicate;

    }
    //endregion Overrides

    //region Static Methods

    /**
     * Creates a state matcher that only compares the block and the given IProperties.
     *
     * @param displayState      the state to compare to.
     * @param propertiesToMatch the properties to match.
     * @return the created state matcher.
     */
    public static PentacleStateMatcher fromState(BlockState displayState, IProperty... propertiesToMatch) {
        return new PentacleStateMatcher(displayState,
                (actualState) -> actualState.getBlock() == displayState.getBlock() &&
                                 matchesProperties(actualState, displayState));
    }

    /**
     * Creates a state matcher that only compares the block and glyph types.
     *
     * @param displayState the state to compare to.
     * @return the created state matcher.
     */
    public static PentacleStateMatcher glyphType(BlockState displayState) {
        return fromState(displayState, BlockChalkGlyph.TYPE);
    }

    /**
     * Compares only the given properties.
     *
     * @param actualState       the actual state to compare with the display state.
     * @param displayState      the display state to compare to.
     * @param propertiesToMatch the properties to match.
     * @return true if all properties on the display state exist identically on the reference state.
     */
    public static boolean matchesProperties(BlockState actualState, BlockState displayState,
                                            IProperty<?>... propertiesToMatch) {
        for (IProperty<?> propertyKey : propertiesToMatch) {
            //If the actual state is missing the key, or the values differ, return false
            if (!displayState.getProperties().containsKey(propertyKey)) {
                Occultism.logger.error("PentacleStateMatcher.matchesProperties: Display state " + displayState +
                                       " does not contain matching property " + propertyKey);
                return false;
            }
            if (!actualState.getProperties().containsKey(propertyKey) ||
                displayState.getValue(propertyKey) != actualState.getValue(propertyKey))
                return false;
        }
        //If there were no mismatches, we're ok.
        return true;
    }
    //endregion Static Methods
}
