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

package com.craftlyworks.hypsum.core.placeholder;

import com.craftlyworks.hypsum.api.HypsumApi;
import com.craftlyworks.hypsum.api.placeholder.Placeholder;
import com.craftlyworks.hypsum.core.exception.DuplicatePlaceholderException;
import com.craftlyworks.hypsum.core.exception.InvalidEngineException;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class PlaceholderEngine implements HypsumApi {
    private final Map<String, Placeholder> placeholders = new ConcurrentHashMap<>();
    private final HytaleLogger logger;
    private final char delimiter;
    private boolean valid = true;

    private PlaceholderEngine(HytaleLogger logger, char delimiter) {
        this.logger = logger;
        this.delimiter = delimiter;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void registerPlaceholder(@Nonnull Placeholder placeholder) {
        if (!valid) {
            throw new InvalidEngineException("Cannot register placeholder: PlaceholderEngine is no longer valid.");
        }
        if (placeholders.containsKey(placeholder.getIdentifier())) {
            throw new DuplicatePlaceholderException("Placeholder with identifier '" + placeholder.getIdentifier() + "' is already registered.");
        }
        placeholders.put(placeholder.getIdentifier(), placeholder);
        if (logger != null) {
            logger.at(Level.INFO).log("Placeholder registered for '%s'", placeholder.getIdentifier());
        }
    }

    @Override
    public void unregisterPlaceholder(@Nonnull String identifier) {
        if (!valid) {
            throw new InvalidEngineException("Cannot unregister placeholder: PlaceholderEngine is no longer valid.");
        }
        placeholders.remove(identifier);
        if (logger != null) {
            logger.at(Level.INFO).log("Placeholder unregistered for '%s'", identifier);
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    public void invalidate() {
        this.valid = false;
        this.placeholders.clear();
    }

    @Override
    public String process(@Nullable PlayerRef player, @Nonnull String text) {
        return PlaceholderProcessor.process(player, text, placeholders, delimiter);
    }

    public static class Builder {
        private HytaleLogger logger;
        private char delimiter = '%';

        public Builder logger(HytaleLogger logger) {
            this.logger = logger;
            return this;
        }

        public Builder delimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public PlaceholderEngine build() {
            return new PlaceholderEngine(logger, delimiter);
        }
    }
}
