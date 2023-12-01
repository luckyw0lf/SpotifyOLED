package javaio;

import javaio.api.SpotifyApi;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SpotifyState
{

    public static Boolean isPlaying = false;
    public static String repeatMode = "off";
    public static Boolean shuffleMode = false;

    public static String songName = "";
    public static String artistName = "";

    public static int currentProgressMs = 1;
    public static int songDuration = 1;
    public static int volumePercent = 0;

    public static AudioFeatures audioFeatures;

    public static float bpm = 0;

    public static String albumcover = "";

    public static void init(){
        Timer pollingTimer = new Timer(true);


        pollingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // if authenticated
                if(!SpotifyApi.authenticated)
                    return;

                CurrentlyPlayingContext currentlyPlaying = SpotifyApi.getCurrentlyPlaying();

                try {
                    isPlaying = currentlyPlaying.getIs_playing();
                    songName = currentlyPlaying.getItem().getName();
                    artistName = ((Track) currentlyPlaying.getItem()).getArtists()[0].getName();
                    currentProgressMs = currentlyPlaying.getProgress_ms();
                    songDuration = currentlyPlaying.getItem().getDurationMs();
                    albumcover = ((Track) currentlyPlaying.getItem()).getAlbum().getImages()[0].getUrl();
                    volumePercent = currentlyPlaying.getDevice().getVolume_percent();
                    repeatMode = currentlyPlaying.getRepeat_state();
                    shuffleMode = currentlyPlaying.getShuffle_state();

                    if(!Objects.equals(songName, currentlyPlaying.getItem().getName())){
                        audioFeatures = SpotifyApi.getAudioFeatures(currentlyPlaying.getItem().getId());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000, 3000);

        Timer interpolatingTimer = new Timer(true);
        interpolatingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(isPlaying)
                    currentProgressMs+=100;
            }
        }, 100, 100);
    }

    public static String formatProgress(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        String currentTime = timeFormat.format(new Date(currentProgressMs));
        String totalDuration = timeFormat.format(new Date(songDuration));

        return currentTime + "/" + totalDuration;
    }
}
