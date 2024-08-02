/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.emi;

import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiIntegrationImpl;

public interface OccultismEmiIntegration {

    OccultismEmiIntegration instance = new OccultismEmiIntegrationImpl();

    static OccultismEmiIntegration get() {
        return instance;
    }

    boolean isLoaded();

    String getFilterText();

    void setFilterText(String filter);
}
