package javaio.states;

import de.pi3g.pi.oled.Font;
import static javaio.DisplayController.display;

public class StartingWebserver extends State
{

    @Override
    public void show()
    {
        display.drawStringCentered("Loading...", Font.FONT_5X8, display.getHeight() / 2, true);
    }
}
