package javaio;

import de.pi3g.pi.oled.OLEDDisplay;
import javaio.menu.MenuItemRepeatMode;
import javaio.menu.MenuItemVolumeAdjust;
import javaio.menu.SpotifyMenuState;

import java.io.IOException;

public class DisplayController
{
    public static ViewState state = ViewState.STARTING_WEBSERVER;
    public static OLEDDisplay display;

    public DisplayController(OLEDDisplay display)
    {
        DisplayController.display = display;
    }

    public void update()
    {
        display.clear();

        state.show();

        try
        {
            display.update();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
