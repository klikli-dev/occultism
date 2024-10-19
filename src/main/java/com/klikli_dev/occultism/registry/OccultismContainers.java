/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.registry;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.klikli_dev.occultism.common.blockentity.StableWormholeBlockEntity;
import com.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelT1Container;
import com.klikli_dev.occultism.common.container.satchel.RitualSatchelT2Container;
import com.klikli_dev.occultism.common.container.satchel.StorageSatchelContainer;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.klikli_dev.occultism.common.container.satchel.AbstractSatchelContainer;
import com.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class OccultismContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(
            BuiltInRegistries.MENU, Occultism.MODID);

    public static final Supplier<MenuType<StorageControllerContainer>> STORAGE_CONTROLLER =
            CONTAINERS.register("storage_controller",
                    () -> IMenuTypeExtension
                            .create((windowId, inv, data) -> new StorageControllerContainer(windowId, inv,
                                    (StorageControllerBlockEntity) inv.player.level()
                                            .getBlockEntity(data.readBlockPos()))));

    public static final Supplier<MenuType<StableWormholeContainer>> STABLE_WORMHOLE =
            CONTAINERS.register("stable_wormhole",
                    () -> IMenuTypeExtension
                            .create((windowId, inv, data) -> new StableWormholeContainer(windowId, inv,
                                    (StableWormholeBlockEntity) inv.player.level()
                                            .getBlockEntity(data.readBlockPos()))));

    public static final Supplier<MenuType<StorageRemoteContainer>> STORAGE_REMOTE =
            CONTAINERS.register("storage_remote",
                    () -> IMenuTypeExtension
                            .create((windowId, inv, data) -> new StorageRemoteContainer(windowId, inv, data.readVarInt())));

    public static final Supplier<MenuType<SpiritContainer>> SPIRIT =
            CONTAINERS.register("spirit",
                    () -> IMenuTypeExtension
                            .create((windowId, inv, data) -> {
                                return new SpiritContainer(windowId, inv,
                                        (SpiritEntity) inv.player.level().getEntity(data.readInt()));
                            }));

    public static final Supplier<MenuType<SpiritTransporterContainer>> SPIRIT_TRANSPORTER =
            CONTAINERS.register("spirit_transporter",
                    () -> IMenuTypeExtension
                            .create((windowId, inv, data) -> {
                                return new SpiritTransporterContainer(windowId, inv,
                                        (SpiritEntity) inv.player.level().getEntity(data.readInt()));
                            }));

    public static final Supplier<MenuType<DimensionalMineshaftContainer>> OTHERWORLD_MINER =
            CONTAINERS.register("otherworld_miner",
                    () -> IMenuTypeExtension
                            .create((windowId, inv, data) -> new DimensionalMineshaftContainer(windowId, inv,
                                    (DimensionalMineshaftBlockEntity) inv.player.level().getBlockEntity(
                                            data.readBlockPos()))));

    public static final Supplier<MenuType<AbstractSatchelContainer>> SATCHEL =
            CONTAINERS.register("satchel", () -> IMenuTypeExtension.create(StorageSatchelContainer::createClientContainer));

    public static final Supplier<MenuType<RitualSatchelT1Container>> RITUAL_SATCHEL_T1 =
            CONTAINERS.register("ritual_satchel_t1", () -> IMenuTypeExtension.create(RitualSatchelT1Container::createClientContainer));

    public static final Supplier<MenuType<RitualSatchelT2Container>> RITUAL_SATCHEL_T2 =
            CONTAINERS.register("ritual_satchel_t2", () -> IMenuTypeExtension.create(RitualSatchelT2Container::createClientContainer));
}
