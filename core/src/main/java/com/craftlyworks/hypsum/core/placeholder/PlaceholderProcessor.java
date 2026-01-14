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

import com.craftlyworks.hypsum.api.placeholder.Placeholder;
import com.hypixel.hytale.server.core.entity.entities.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class PlaceholderProcessor {

    private PlaceholderProcessor() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static @Nonnull String process(@Nullable Player player, @Nonnull String text, @Nonnull Map<String, Placeholder> placeholders, char delimiter) {
        if (text.isEmpty() || placeholders.isEmpty()) {
            return text;
        }

        StringBuilder builder = new StringBuilder(text.length() + 16);
        int length = text.length();
        int lastPos = 0;
        int startPos = -1;

        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);

            if (c == delimiter) {
                if (startPos == -1) {
                    // Potential start of a placeholder
                    startPos = i;
                } else {
                    // Potential end of a placeholder
                    String identifier = text.substring(startPos + 1, i);
                    Placeholder placeholder = placeholders.get(identifier);

                    if (placeholder != null) {
                        // Found a valid placeholder
                        builder.append(text, lastPos, startPos);
                        builder.append(getSafeValue(placeholder, player));
                        lastPos = i + 1;
                        startPos = -1;
                    } else {
                        // Not a valid placeholder, treat this '%' as a new potential start
                        startPos = i;
                    }
                }
            }
        }

        // Append the remaining text
        if (lastPos < length) {
            builder.append(text, lastPos, length);
        }

        return builder.toString();
    }

    private static String getSafeValue(@Nonnull Placeholder placeholder, @Nullable Player player) {
        try {
            String value = placeholder.getValue(player);
            return value != null ? value : "UNKNOWN";
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
