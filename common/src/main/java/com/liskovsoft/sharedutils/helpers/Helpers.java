package com.liskovsoft.sharedutils.helpers;

import java.lang.reflect.Field;

/**
 * Minimal stub replacement for the original Helpers class that existed
 * in the proprietary sharedutils module. It only implements the methods
 * referenced by the patched ExoPlayer sources so the project compiles.
 */
public final class Helpers {
    private Helpers() {}

    /**
     * Sets a private/protected field on an object via reflection. Any
     * exceptions are swallowed because the patch code that uses this util
     * is best-effort.
     */
    public static void setField(Object target, String fieldName, Object value) {
        if (target == null || fieldName == null) return;
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception ignored) {
            // If reflection fails we just ignore – worst case the patch
            // (range workaround) is not applied but playback still works.
        }
    }
}
