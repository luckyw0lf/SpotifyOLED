package javaio.states;

import de.pi3g.pi.oled.Font;
import javaio.SpotifyState;
import javaio.graphics.DrawableAssetData;
import javaio.menu.*;

import static javaio.DisplayController.display;

public class Menu extends State
{
    @Override
    public void show()
    {
        if (!SpotifyMenuState.isSelected)
        {
            MenuItemVolumeAdjust.selectedVolume = SpotifyState.volumePercent;
            MenuItemRepeatMode.changeContext(SpotifyState.repeatMode);
            MenuItemShuffleMode.changeMode(SpotifyState.shuffleMode);

            int i = 0;
            for (MenuItem item : SpotifyMenuState.menuItems)
            {
                item.updateDisplayValue();
                display.drawStringCentered(item.displayValue.toUpperCase(), Font.FONT_4X5, i * MenuItem.height + 20, true);
                i++;
            }

            DrawableAssetData.SELECTED_MENU_ITEM_BOX.drawDataCentered(display, display.getWidth() / 2, SpotifyMenuState.position * MenuItem.height + 20 + 7);
            return;
        }

        display.drawStringCentered(SpotifyMenuState.selected.displayValue, Font.FONT_5X8, display.getHeight() / 2, true);
    }
}
