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
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExamplePlugin extends JavaPlugin {

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
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
                public String getValue(@Nullable PlayerRef player) {
                    if (player == null) {
                        return "Hello Guest from Example Plugin!";
                    }
                    return "Hello " + player.getUsername() + " from Example Plugin!";
                }
            });
        }
        this.getEventRegistry().registerGlobal(PlayerChatEvent.class, event -> {
            PlayerRef player = event.getSender();
            String originalMessage = event.getContent();
            String processedMessage = api != null ? api.process(player, originalMessage) : null;
            player.sendMessage(Message.raw(processedMessage != null ? processedMessage : ""));
        });
    }
}
