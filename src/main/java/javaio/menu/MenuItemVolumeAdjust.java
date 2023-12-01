package javaio.menu;

import javaio.SpotifyState;
import javaio.api.SpotifyApi;

public class MenuItemVolumeAdjust extends MenuItem {

    public static int selectedVolume = 0;

    public MenuItemVolumeAdjust(String displayText)
    {
        super(displayText);
    }

    @Override
    public void previous() {
        selectedVolume = Math.max(selectedVolume-10, 0);
        updateDisplayValue();
    }

    @Override
    public void next() {
        selectedVolume = Math.min(selectedVolume+10, 100);
        updateDisplayValue();
    }

    @Override
    public void action() {
        SpotifyApi.setVolume(selectedVolume);
        SpotifyState.volumePercent = selectedVolume;
    }

    @Override
    public void chosen()
    {
        selectedVolume = SpotifyState.volumePercent;
        updateDisplayValue();
    }

    @Override
    public void updateDisplayValue()
    {
        displayValue = name + ": " + selectedVolume;
    }
}
