// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.emi;

public class OccultismEmiIntegrationDummy implements OccultismEmiIntegration {

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
