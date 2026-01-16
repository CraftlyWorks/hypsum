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
import com.craftlyworks.hypsum.core.exception.DuplicatePlaceholderException;
import com.craftlyworks.hypsum.core.exception.InvalidEngineException;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlaceholderEngineTest {

    private PlaceholderEngine.Builder createBuilder() {
        return PlaceholderEngine.builder();
    }

    @Test
    void testProcess() {
        PlaceholderEngine manager = createBuilder().build();
        String input = "Hello world";
        assertEquals(input, manager.process(null, input));
    }

    @Test
    void testPerPlayerPlaceholder() {
        PlaceholderEngine manager = createBuilder().build();
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "player_name";
            }

            @Override
            public String getValue(PlayerRef player) {
                // In a real scenario we'd use player.getDisplayName()
                // but for testing we just check if it's NOT null or some other logic
                return player != null ? "MockPlayer" : "Unknown";
            }
        });

        // We use null because instantiating Player class triggers Hytale's static initializers
        // which fail in a test environment without proper setup.
        // But our logic should still work if we pass null and handle it.
        assertEquals("Hello Unknown", manager.process(null, "Hello %player_name%"));
    }

    @Test
    void testDuplicateRegistration() {
        PlaceholderEngine manager = createBuilder().build();
        Placeholder p1 = new Placeholder() {
            @Override
            public String getIdentifier() {
                return "duplicate";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "v1";
            }
        };

        Placeholder p2 = new Placeholder() {
            @Override
            public String getIdentifier() {
                return "duplicate";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "v2";
            }
        };

        manager.registerPlaceholder(p1);
        assertThrows(DuplicatePlaceholderException.class, () -> manager.registerPlaceholder(p2));
    }

    @Test
    void testInvalidEngineRegistration() {
        PlaceholderEngine manager = createBuilder().build();
        manager.invalidate();
        Placeholder p = new Placeholder() {
            @Override
            public String getIdentifier() {
                return "test";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "v";
            }
        };
        assertThrows(InvalidEngineException.class, () -> manager.registerPlaceholder(p));
    }

    @Test
    void testRecursivePrevention() {
        PlaceholderEngine manager = createBuilder().build();
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "first";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "and %second%";
            }
        });
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "second";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "value";
            }
        });

        // If it was recursive, it would return "and value"
        // But the requirement is that it shouldn't trigger other placeholders
        assertEquals("and %second% value", manager.process(null, "%first% %second%"));
    }

    @Test
    void testCustomDelimiter() {
        PlaceholderEngine manager = createBuilder().delimiter('{').build();
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "test";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "success";
            }
        });

        assertEquals("success", manager.process(null, "{test{"));
        assertEquals("{test", manager.process(null, "{test"));
        assertEquals("test{", manager.process(null, "test{"));
        assertEquals("normal text", manager.process(null, "normal text"));
    }

    @Test
    void testUnregisterPlaceholder() {
        PlaceholderEngine manager = createBuilder().build();
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "test";
            }

            @Override
            public String getValue(PlayerRef player) {
                return "value";
            }
        });

        assertEquals("value", manager.process(null, "%test%"));
        manager.unregisterPlaceholder("test");
        assertEquals("%test%", manager.process(null, "%test%"));
    }

    @Test
    void testNullPlaceholderValue() {
        PlaceholderEngine manager = createBuilder().build();
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "null_val";
            }

            @Override
            public String getValue(PlayerRef player) {
                return null;
            }
        });

        assertEquals("UNKNOWN", manager.process(null, "%null_val%"));
    }

    @Test
    void testExplodingPlaceholderValue() {
        PlaceholderEngine manager = createBuilder().build();
        manager.registerPlaceholder(new Placeholder() {
            @Override
            public String getIdentifier() {
                return "boom";
            }

            @Override
            public String getValue(PlayerRef player) {
                throw new RuntimeException("Explosion!");
            }
        });

        assertEquals("UNKNOWN", manager.process(null, "%boom%"));
    }
}
