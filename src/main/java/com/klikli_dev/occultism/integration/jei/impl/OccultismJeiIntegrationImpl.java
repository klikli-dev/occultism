// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.occultism.integration.jei.impl;

import com.google.common.base.Strings;
import com.klikli_dev.modonomicon.platform.Services;
import com.klikli_dev.occultism.integration.jei.OccultismJeiIntegration;
import mezz.jei.api.runtime.IJeiRuntime;

public class OccultismJeiIntegrationImpl implements OccultismJeiIntegration {
    public boolean isLoaded() {
        return Services.PLATFORM.isModLoaded("jei");
    }

    @Override
    public String getFilterText() {
        if (!this.isLoaded())
            return "";

        return OccultismJeiHelper.getFilterText();
    }

    @Override
    public void setFilterText(String filter) {
        if (!this.isLoaded())
            return;

        OccultismJeiHelper.setFilterText(filter);
    }

    public static class OccultismJeiHelper {
        public static String getFilterText() {
            IJeiRuntime runtime = JeiPlugin.getJeiRuntime();
            if (runtime != null)
                return runtime.getIngredientFilter().getFilterText();
            return "";
        }

        public static void setFilterText(String filter) {
            IJeiRuntime runtime = JeiPlugin.getJeiRuntime();
            if (runtime != null)
                runtime.getIngredientFilter().setFilterText(Strings.nullToEmpty(filter));
        }
    }

}
