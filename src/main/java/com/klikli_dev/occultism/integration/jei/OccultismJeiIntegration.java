/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.integration.jei;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.platform.Services;
import com.klikli_dev.occultism.Occultism;

public interface OccultismJeiIntegration {

    static OccultismJeiIntegration get() {
        return Holder.INSTANCE;
    }

    private static OccultismJeiIntegration create() {
        if (!Services.PLATFORM.isModLoaded("jei")) {
            return new OccultismJeiIntegrationDummy();
        }

        try {
            return (OccultismJeiIntegration) Class.forName("com.klikli_dev.occultism.integration.jei.impl.OccultismJeiIntegrationImpl")
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException | LinkageError e) {
            Occultism.LOGGER.warn("Failed to initialize JEI integration, falling back to dummy implementation.", e);
            return new OccultismJeiIntegrationDummy();
        }
    }

    boolean isLoaded();

    String getFilterText();

    void setFilterText(String filter);

    final class Holder {
        private static final OccultismJeiIntegration INSTANCE = create();

        private Holder() {
        }
    }
}
