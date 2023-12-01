package javaio.graphics;

import de.pi3g.pi.oled.OLEDDisplay;

public interface DrawableAsset
{
    void drawData(OLEDDisplay display, int offsetX, int offsetY);
    public void drawDataCentered(OLEDDisplay display, int offsetX, int offsetY);
}
