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

package com.craftlyworks.hypsum.example;

import com.craftlyworks.hypsum.api.HypsumApi;
import com.craftlyworks.hypsum.api.HypsumProvider;
import com.craftlyworks.hypsum.api.placeholder.Placeholder;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class ExamplePlugin extends JavaPlugin {

    public ExamplePlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void start() {
        HypsumApi api = HypsumProvider.get();
        if (api != null) {
            api.registerPlaceholder(new Placeholder() {
                @Override
                public String getIdentifier() {
                    return "example_hello";
                }

                @Override
                public String getValue(Player player) {
                    return "Hello " + player.getDisplayName() + " from Example Plugin!";
                }
            });

            // Demonstrate how to use the API to process a string
            // String processed = api.process(somePlayer, "Welcome %example_hello%!");
        }
    }
}
