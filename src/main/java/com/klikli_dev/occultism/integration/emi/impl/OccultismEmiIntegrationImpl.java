// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.emi.impl;

import com.klikli_dev.occultism.integration.emi.OccultismEmiIntegration;
import com.klikli_dev.occultism.integration.jei.impl.JeiPlugin;
import dev.emi.emi.api.EmiApi;
import mezz.jei.api.runtime.IJeiRuntime;
import net.neoforged.fml.ModList;

public class OccultismEmiIntegrationImpl implements OccultismEmiIntegration {
    public boolean isLoaded() {
        return ModList.get().isLoaded("emi");
    }

    @Override
    public String getFilterText() {
        if (!this.isLoaded())
            return "";

        return OccultismEmiHelper.getFilterText();
    }

    @Override
    public void setFilterText(String filter) {
        if (!this.isLoaded())
            return;

        OccultismEmiHelper.setFilterText(filter);
    }

    public static class OccultismEmiHelper {
        public static String getFilterText() {
            return EmiApi.getSearchText();
        }

        public static void setFilterText(String filter) {
            EmiApi.setSearchText(filter);
        }
    }

}
