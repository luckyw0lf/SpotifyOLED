package javaio.menu;

import javaio.SpotifyState;
import javaio.api.SpotifyApi;

import java.util.ArrayList;

public class MenuItemRepeatMode extends MenuItem
{
    public static ArrayList<String> modes = new ArrayList<>();

    static {
        modes.add("off");
        modes.add("context");
        modes.add("track");
    }

    public static int position = 0;

    public MenuItemRepeatMode(String displayText)
    {
        super(displayText);
        updateDisplayValue();
    }

    @Override
    public void previous()
    {
        position = Math.max(0, position-1);
        updateDisplayValue();
    }

    @Override
    public void next()
    {
        position = Math.min(modes.size()-1, position+1);
        updateDisplayValue();
    }

    @Override
    public void action()
    {
        SpotifyApi.setRepeatMode(modes.get(position));
        SpotifyState.repeatMode = modes.get(position);
    }

    @Override
    public void chosen()
    {

    }

    @Override
    public void updateDisplayValue()
    {
        displayValue = name + ": " + modes.get(position);
    }

    public static void changeContext(String context){
        position = modes.indexOf(context);
    }
}
