package javaio.states;

import de.pi3g.pi.oled.Font;
import javaio.api.WebApi;

import static javaio.DisplayController.display;

public class AwaitingAuthentication extends State
{
    @Override
    public void show()
    {
        display.drawStringCentered(WebApi.host + ":" + WebApi.port, Font.FONT_5X8, display.getHeight() / 2, true);
    }
}
