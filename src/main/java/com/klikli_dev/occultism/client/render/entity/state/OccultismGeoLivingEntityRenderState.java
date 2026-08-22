/*
 * SPDX-FileCopyrightText: 2026 klikli-dev
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.occultism.client.render.entity.state;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import java.util.Map;

public class OccultismGeoLivingEntityRenderState extends LivingEntityRenderState implements GeoRenderState {
    private final Map<DataTicket<?>, Object> geckolibData = new Reference2ObjectOpenHashMap<>();
    public String jobID = "";

    @Override
    public <D> void addGeckolibData(DataTicket<D> dataTicket, D data) {
        this.geckolibData.put(dataTicket, data);
    }

    @Override
    public boolean hasGeckolibData(DataTicket<?> dataTicket) {
        return this.geckolibData.containsKey(dataTicket);
    }

    @Override
    public int getPackedLight() {
        return this.getOrDefaultGeckolibData(DataTickets.PACKED_LIGHT, this.lightCoords);
    }

    @Override
    public double getAnimatableAge() {
        return this.getOrDefaultGeckolibData(DataTickets.TICK, (double) this.ageInTicks);
    }

    @Override
    public Map<DataTicket<?>, Object> getDataMap() {
        return this.geckolibData;
    }
}
