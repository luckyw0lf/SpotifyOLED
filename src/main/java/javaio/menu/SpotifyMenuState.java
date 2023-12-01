package javaio.menu;

import java.util.ArrayList;

public class SpotifyMenuState
{
    public static ArrayList<MenuItem> menuItems = new ArrayList<>();
    public static int position = 0;
    public static MenuItem selected;
    public static boolean isSelected = false;
    public static void init(){
        menuItems.add(new MenuItemVolumeAdjust("VOLUME"));
        menuItems.add(new MenuItemRepeatMode("REPEAT"));
        menuItems.add(new MenuItemShuffleMode("SHUFFLE"));
    }

    public static void menuNext(){
        if(isSelected)
            selected.next();
        if(!isSelected)
            position = Math.min(menuItems.size()-1, position+1);
    }

    public static void menuAction() {
        if(isSelected){
            selected.action();
            selected = null;
            isSelected = false;
            return;
        }

        selected = menuItems.get(position);
        selected.chosen();
        isSelected = true;
    }
    public static void menuPrevious(){
        if(isSelected)
            selected.previous();
        if(!isSelected)
            position = Math.max(0, position-1);
    }

    public static void reset(){
        selected = null;
        isSelected = false;
        position = 0;
    }
}
