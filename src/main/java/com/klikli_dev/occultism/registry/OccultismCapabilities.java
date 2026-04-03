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

import com.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
//import top.theillusivec4.curios.api.CuriosCapability; // TODO: re-enable when Curios is available for 26.1

public class OccultismCapabilities {

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {

        // TODO: re-enable when Curios is available for 26.1
        //event.registerItem(
        //        CuriosCapability.ITEM, // capability to register for
        //        (itemStack, context) -> {
        //            return new FamiliarRingItem.Curio(itemStack);
        //        },
        //        // items to register for
        //        OccultismItems.FAMILIAR_RING.get());

        // NeoForge 26.1 moved `neoforge:item_handler` to the transfer API using
        // ResourceHandler<ItemResource>. These legacy IItemHandler capability
        // registrations must stay disabled until the affected inventories are
        // migrated to Capabilities.Item.BLOCK / ENTITY providers.

    }

}
