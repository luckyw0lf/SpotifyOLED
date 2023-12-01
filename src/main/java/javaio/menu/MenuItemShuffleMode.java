package javaio.menu;

import javaio.SpotifyState;
import javaio.api.SpotifyApi;

import java.util.ArrayList;
import java.util.Objects;

public class MenuItemShuffleMode extends MenuItem
{
    public static ArrayList<String> modes = new ArrayList<>();

    static {
        modes.add("off");
        modes.add("on");
    }

    public static int position = 0;

    public MenuItemShuffleMode(String displayText)
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
        SpotifyApi.setShuffleMode(Objects.equals(modes.get(position), "on"));
        SpotifyState.shuffleMode = Objects.equals(modes.get(position), "on");
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

    public static void changeMode(Boolean mode){
        position = mode ? 1 : 0;
    }
}
