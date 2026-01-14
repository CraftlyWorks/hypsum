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

import com.craftlyworks.hypsum.api.exception.ApiNotRegisteredException;

public final class HypsumProvider {
    private static HypsumApi api;

    private HypsumProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Gets the current HypsumApi instance.
     *
     * @return the HypsumApi instance
     * @throws ApiNotRegisteredException if the API is not registered
     */
    public static HypsumApi get() {
        if (api == null) {
            throw new ApiNotRegisteredException("HypsumApi is not registered! Is Hypsum plugin loaded?");
        }
        return api;
    }

    /**
     * Registers a HypsumApi instance.
     *
     * @param instance the instance to register
     */
    public static void register(HypsumApi instance) {
        api = instance;
    }

    /**
     * Unregisters the current HypsumApi instance.
     */
    public static void unregister() {
        api = null;
    }
}
