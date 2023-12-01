package javaio.api;

import javaio.DisplayController;
import javaio.ViewState;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;

import java.io.IOException;

public class SpotifyApi
{
    public static boolean authenticated = false;
    private static se.michaelthelin.spotify.SpotifyApi api;
    private static String scopes;

    public static void setApiCredentials(String clientId, String clientSecret, String redirectURI, String scopes)
    {
        SpotifyApi.scopes = scopes;
        api = se.michaelthelin.spotify.SpotifyApi.builder().
                setClientId(clientId).
                setClientSecret(clientSecret).
                setRedirectUri(SpotifyHttpManager.makeUri(redirectURI)).build();
    }

    public static String getAuthCodeURI()
    {
        return api.authorizationCodeUri().scope(scopes).show_dialog(true).build().execute().toString();
    }

    public static void authenticate(String authCode)
    {
        try
        {
            AuthorizationCodeCredentials authorizationCodeCredentials = api.authorizationCode(authCode).build().execute();
            api.setAccessToken(authorizationCodeCredentials.getAccessToken());
            api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            DisplayController.state = ViewState.PLAYBACK;
            authenticated = true;
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void playNext()
    {
        try
        {
            api.skipUsersPlaybackToNextTrack().build().execute();
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void playPrevious()
    {
        try
        {
            api.skipUsersPlaybackToPreviousTrack().build().execute();
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void setPlaying(boolean playing)
    {
        try
        {
            if (playing)
            {
                api.startResumeUsersPlayback().build().execute();
                return;
            }

            api.pauseUsersPlayback().build().execute();
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void setVolume(int volume)
    {
        try
        {
            api.setVolumeForUsersPlayback(volume).build().execute();
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static CurrentlyPlayingContext getCurrentlyPlaying()
    {
        try
        {
            return api.getInformationAboutUsersCurrentPlayback().build().execute();
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static AudioFeatures getAudioFeatures(String id){
        try
        {
            return api.getAudioFeaturesForTrack(id).build().execute();
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setShuffleMode(Boolean mode){
        try
        {
            api.toggleShuffleForUsersPlayback(mode).build().execute();
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            e.printStackTrace();
        }
    }

    public static void setRepeatMode(String mode)
    {
        try
        {
            api.setRepeatModeOnUsersPlayback(mode).build().execute();
        }
        catch (IOException | ParseException | SpotifyWebApiException e)
        {
            e.printStackTrace();
        }
    }
}
