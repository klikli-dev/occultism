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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.command.DebugAICommand;
import com.github.klikli_dev.occultism.common.command.NbtCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class OccultismCommands {
    //region Static Methods
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        //register dispatcher for subcommands of /occultism debug
        LiteralCommandNode<CommandSourceStack> debugCommand = dispatcher.register(
                Commands.literal("debug")
                        .then(DebugAICommand.register(dispatcher))
        );

        //register dispatcher for subcommands of /occultism
        LiteralCommandNode<CommandSourceStack> occultismCommand = dispatcher.register(
                Commands.literal(Occultism.MODID)
                        .then(NbtCommand.register(dispatcher))
                        .then(debugCommand)
        );

        //register /occultism for dispatching
        dispatcher.register(Commands.literal("occultism").redirect(occultismCommand));
    }
    //endregion Static Methods
}
