/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.jei;

import com.klikli_dev.occultism.integration.jei.impl.OccultismJeiIntegrationImpl;

public interface OccultismJeiIntegration {

    OccultismJeiIntegration instance = new OccultismJeiIntegrationImpl();

    static OccultismJeiIntegration get() {
        return instance;
    }

    boolean isLoaded();

    String getFilterText();

    void setFilterText(String filter);
}
