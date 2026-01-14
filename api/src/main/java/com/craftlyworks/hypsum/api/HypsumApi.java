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

package com.craftlyworks.hypsum.api;

import com.craftlyworks.hypsum.api.placeholder.Placeholder;
import com.hypixel.hytale.server.core.entity.entities.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface HypsumApi {
    /**
     * Registers a new placeholder.
     *
     * @param placeholder the placeholder to register
     */
    void registerPlaceholder(@Nonnull Placeholder placeholder);

    /**
     * Unregisters a placeholder.
     *
     * @param placeholder the placeholder to unregister
     */
    default void unregisterPlaceholder(@Nonnull Placeholder placeholder) {
        unregisterPlaceholder(placeholder.getIdentifier());
    }

    /**
     * Unregisters a placeholder by its identifier.
     *
     * @param identifier the identifier of the placeholder to unregister
     */
    void unregisterPlaceholder(@Nonnull String identifier);

    /**
     * Processes placeholders in the given text for the specified player.
     *
     * @param player the player context
     * @param text   the text to process
     * @return the processed text with placeholders replaced
     */
    String process(@Nullable Player player, @Nonnull String text);

    /**
     * Checks if the API instance is still valid.
     *
     * @return true if valid, false otherwise
     */
    default boolean isValid() {
        return true;
    }
}
