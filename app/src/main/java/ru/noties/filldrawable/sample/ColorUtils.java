package ru.noties.filldrawable.sample;

/**
 * Created by Dimitry Ivanov on 26.10.2015.
 */
public class ColorUtils {

    private ColorUtils() {}

    public static int applyAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | (alpha << 24);
    }
}
