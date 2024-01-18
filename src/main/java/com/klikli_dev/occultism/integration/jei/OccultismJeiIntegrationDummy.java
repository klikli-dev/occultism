// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.jei;

public class OccultismJeiIntegrationDummy implements OccultismJeiIntegration {

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public String getFilterText() {
        return "";
    }

    @Override
    public void setFilterText(String filter) {

    }
}
