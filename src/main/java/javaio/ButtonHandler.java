package javaio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;
import javaio.api.SpotifyApi;
import javaio.menu.SpotifyMenuState;

public class ButtonHandler
{
    private static Button buttonAction;
    private static Button buttonNext;
    private static Button buttonPrevious;

    public static void init(GpioController gpioController){

        buttonPrevious = new Button(isLongPress -> {
            switch (DisplayController.state) {
                case PLAYBACK -> playbackPrevious(isLongPress);
                case MENU -> menuPrevious(isLongPress);
            }
        }, gpioController, RaspiPin.GPIO_02);

        buttonAction = new Button(isLongPress -> {
            switch (DisplayController.state) {
                case PLAYBACK -> playbackPause(isLongPress);
                case MENU -> menuAction(isLongPress);
            }
        }, gpioController, RaspiPin.GPIO_01);

        buttonNext = new Button(isLongPress -> {
            switch (DisplayController.state) {
                case PLAYBACK -> playbackNext(isLongPress);
                case MENU -> menuNext(isLongPress);
            }
        }, gpioController, RaspiPin.GPIO_00);
    }

    private static void playbackPrevious(boolean isLongPress){
        SpotifyApi.playPrevious();
    }
    private static void playbackPause(boolean isLongPress){
        if(isLongPress){
            DisplayController.state = ViewState.MENU;
            return;
        }
        SpotifyApi.setPlaying(!SpotifyState.isPlaying);
        SpotifyState.isPlaying = !SpotifyState.isPlaying;
    }

    private static void playbackNext(boolean isLongPress){
        SpotifyApi.playNext();
    }
    private static void menuPrevious(boolean isLongPress){
        SpotifyMenuState.menuPrevious();
    }
    private static void menuNext(boolean isLongPress){
        SpotifyMenuState.menuNext();
    }


    private static void menuAction(boolean isLongPress){
        if(isLongPress){
            DisplayController.state = ViewState.PLAYBACK;
            SpotifyMenuState.reset();
            return;
        }

        SpotifyMenuState.menuAction();
    }
}
