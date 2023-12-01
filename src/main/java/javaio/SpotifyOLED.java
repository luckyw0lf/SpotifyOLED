package javaio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.i2c.I2CFactory;
import de.pi3g.pi.oled.OLEDDisplay;
import javaio.api.WebApi;
import javaio.api.SpotifyApi;
import javaio.menu.SpotifyMenuState;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SpotifyOLED {

    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException {
        // set up the display, and the controller behind it
        DisplayController displayController = new DisplayController(new OLEDDisplay(1, 0x3c));

        // create a loop that updates the display every 200 ms
        Timer updateLoop = new Timer(false);
        updateLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                displayController.update();
            }
        }, 0, 200);

        // set up the spotifyApi credentials
        String clientID = "3dbe240cdb61497da81646aed45c49c6";
        String clientSecret = "38f8ac867b3c466f9b222ffaf381b65c";
        String redirectURI = "http://rubenpi.local:8080/callback";
        String accessScope = "user-read-currently-playing user-read-playback-state user-modify-playback-state";
        SpotifyApi.setApiCredentials(clientID, clientSecret, redirectURI, accessScope);

        // set up the playback state, so we can get information whenever we are authenticated
        SpotifyState.init();

        // initialize the Api, so we can authenticate through a link
        WebApi.init();

        // initialize the menu items
        SpotifyMenuState.init();

        // initialize the buttons
        GpioController gpioController = GpioFactory.getInstance();
        ButtonHandler.init(gpioController);
    }
}
