package javaio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Button {
    private long lastButtonPress = 0;
    private final int longPressDuration = 500;
    private final int minimumPressDuration = 100;
    private boolean longPressDetected = false;

    public Button(ButtonCallback callback, GpioController gpio, Pin pinnum) {
        GpioPinDigitalInput pin = gpio.provisionDigitalInputPin(pinnum, PinPullResistance.PULL_UP);

        pin.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                long currentTime = System.currentTimeMillis();
                long buttonPressDuration = currentTime - lastButtonPress;

                if (gpioPinDigitalStateChangeEvent.getState().isHigh()) {
                    if (buttonPressDuration >= longPressDuration && !longPressDetected) {
                        longPressDetected = true;
                        callback.run(true); // Indicates a long press
                    } else if (buttonPressDuration >= minimumPressDuration && buttonPressDuration < longPressDuration) {
                        callback.run(false); // Indicates a short press
                    }
                } else {
                    if (buttonPressDuration >= minimumPressDuration) {
                        // Ignore rapid button releases
                        lastButtonPress = currentTime;
                        longPressDetected = false;
                    }
                }
            }
        });
    }

    public interface ButtonCallback {
        void run(boolean isLongPress);
    }
}