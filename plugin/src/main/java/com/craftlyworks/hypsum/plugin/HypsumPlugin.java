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
import com.craftlyworks.hypsum.core.placeholder.PlaceholderEngine;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;
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
        this.getLogger().at(Level.INFO).log("Hypsum plugin started!");
    }

    @Override
    protected void shutdown() {
        HypsumProvider.unregister();
        this.engine.invalidate();
        super.shutdown();
        this.getLogger().at(Level.INFO).log("Hypsum plugin stopped!");
    }

    public PlaceholderEngine getEngine() {
        return engine;
    }
}
