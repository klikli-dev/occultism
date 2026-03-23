/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.emi;

public interface OccultismEmiIntegration {

    OccultismEmiIntegration instance = new OccultismEmiIntegrationDummy();

    static OccultismEmiIntegration get() {
        return instance;
    }

    boolean isLoaded();

    String getFilterText();

    void setFilterText(String filter);
}
