/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.gui.storage;

import com.klikli_dev.occultism.api.common.data.StorageControllerGuiMode;

public class StorageScreenState {
    private StorageControllerGuiMode mode = StorageControllerGuiMode.INVENTORY;
    private int firstVisibleRow = 1;
    private int previousFirstVisibleRow = -1;
    private int maxFirstVisibleRow = 1;
    private boolean searchFocusRequested;
    private long lastInteractionTime;
    private String searchText = "";

    public StorageControllerGuiMode mode() {
        return this.mode;
    }

    public void setMode(StorageControllerGuiMode mode) {
        this.mode = mode;
    }

    public boolean isInventoryMode() {
        return this.mode == StorageControllerGuiMode.INVENTORY;
    }

    public boolean isAutocraftingMode() {
        return this.mode == StorageControllerGuiMode.AUTOCRAFTING;
    }

    public int firstVisibleRow() {
        return this.firstVisibleRow;
    }

    public int maxFirstVisibleRow() {
        return this.maxFirstVisibleRow;
    }

    public void setMaxFirstVisibleRow(int maxFirstVisibleRow) {
        this.maxFirstVisibleRow = Math.max(1, maxFirstVisibleRow);
        this.firstVisibleRow = Math.max(1, Math.min(this.firstVisibleRow, this.maxFirstVisibleRow));
    }

    public boolean trackFirstVisibleRowChange() {
        boolean changed = this.previousFirstVisibleRow != this.firstVisibleRow;
        this.previousFirstVisibleRow = this.firstVisibleRow;
        return changed;
    }

    public void resetDisplayTracking() {
        this.previousFirstVisibleRow = -1;
    }

    public void scrollUp() {
        if (this.firstVisibleRow > 1) {
            this.firstVisibleRow--;
        }
    }

    public void scrollDown() {
        if (this.firstVisibleRow < this.maxFirstVisibleRow) {
            this.firstVisibleRow++;
        }
    }

    public void requestSearchFocus() {
        this.searchFocusRequested = true;
    }

    public boolean consumeSearchFocusRequest() {
        boolean requested = this.searchFocusRequested;
        this.searchFocusRequested = false;
        return requested;
    }

    public void markInteraction(long gameTime) {
        this.lastInteractionTime = gameTime;
    }

    public boolean canInteract(long gameTime, long cooldownMs) {
        return gameTime > this.lastInteractionTime + cooldownMs;
    }

    public String searchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
