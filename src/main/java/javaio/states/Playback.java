package javaio.states;

import de.pi3g.pi.oled.Font;
import javaio.SpotifyState;
import javaio.graphics.DisplayHelper;
import javaio.graphics.DrawableAssetData;

import static javaio.DisplayController.display;

public class Playback extends State
{
    @Override
    public void show()
    {
        int heightOffset = 5;

        int index = 0;
        int lineHeight = 9;

        String[] lines = DisplayHelper.splitIntoLines(SpotifyState.songName, 19);
        for (String line : lines)
        {
            display.drawStringCentered(line, Font.FONT_5X8, heightOffset + index * lineHeight, true);
            index++;
        }

        display.drawStringCentered(SpotifyState.artistName.toUpperCase(), Font.FONT_4X5, (index + 1) * lineHeight, true);

        if (SpotifyState.isPlaying)
        {
            DrawableAssetData.PLAY.drawData(display, 0, display.getHeight());
        }
        else
        {
            DrawableAssetData.PAUSE.drawData(display, 0, display.getHeight());
        }

        // time
        display.drawStringCentered(SpotifyState.formatProgress(), Font.FONT_4X5, display.getHeight() - DrawableAssetData.PROGRESS_BAR.height - 8, true);

        // progress bar
        for (int i = 0; i < Math.round((float) SpotifyState.currentProgressMs / SpotifyState.songDuration * 100); i++)
        {
            display.setPixel((display.getWidth() - DrawableAssetData.PROGRESS_BAR.width) / 2 + i, display.getHeight() - DrawableAssetData.PROGRESS_BAR.height / 2 - 1, true);
            display.setPixel((display.getWidth() - DrawableAssetData.PROGRESS_BAR.width) / 2 + i, display.getHeight() - DrawableAssetData.PROGRESS_BAR.height / 2, true);
        }

        DrawableAssetData.PROGRESS_BAR.drawDataCentered(display, display.getWidth() / 2, display.getHeight());

        // repeat icon
        if (SpotifyState.shuffleMode)
        {
            DrawableAssetData.SHUFFLE.drawData(display, display.getWidth() - DrawableAssetData.SHUFFLE.width, display.getHeight() - 10);
        }

        // repeat icon
        if (SpotifyState.repeatMode == "context" || SpotifyState.repeatMode == "track")
        {
            DrawableAssetData.REPEAT.drawData(display, display.getWidth() - DrawableAssetData.REPEAT.width, display.getHeight());
        }
        else
        {
            DrawableAssetData.NO_REPEAT.drawData(display, display.getWidth() - DrawableAssetData.NO_REPEAT.width, display.getHeight());
        }
    }
}
