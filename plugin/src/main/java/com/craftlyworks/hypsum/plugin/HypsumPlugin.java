/*
 * MIT License
 *
 * Copyright (c) CraftlyWorks
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.craftlyworks.hypsum.plugin;

import com.craftlyworks.hypsum.api.HypsumProvider;
import com.craftlyworks.hypsum.api.placeholder.Placeholder;
import com.craftlyworks.hypsum.core.placeholder.PlaceholderEngine;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Level;

public class HypsumPlugin extends JavaPlugin {
    private final PlaceholderEngine engine;

    public HypsumPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        this.engine = PlaceholderEngine.builder().logger(this.getLogger()).build();
        HypsumProvider.register(this.engine);
    }

    @Override
    protected void start() {
        super.start();
        //---- Default test placeholder ----//
        this.engine.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "hypsum_test";
            }

            @Override
            public String getValue(@Nullable PlayerRef player) {
                return "Hypsum is working!";
            }
        });
        this.getCommandRegistry().registerCommand(new CommandBase("hypsum", "Test Hypsum placeholder processing") {
            @Override
            protected void executeSync(@Nonnull CommandContext ctx) {
                String process = engine.process(null, "This is a test: %hypsum_test%");
                ctx.sender().sendMessage(Message.raw("Original: %hypsum_test% | Processed: " + process));
            }
        });
        this.getLogger().at(Level.INFO).log("Hypsum plugin started!");
    }

    @Override
    protected void shutdown() {
        this.engine.unregisterPlaceholder("hypsum_test");

        HypsumProvider.unregister();
        this.engine.invalidate();
        super.shutdown();
        this.getLogger().at(Level.INFO).log("Hypsum plugin stopped!");
    }

    public PlaceholderEngine getEngine() {
        return engine;
    }
}
