package javaio.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javaio.DisplayController;
import javaio.SpotifyState;
import javaio.ViewState;
import javaio.data.VolumeRequest;
import spark.Spark;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WebApi
{
    public final static int port = 8080;
    public static String host;
    public static void init() {
        Spark.port(port);
        Spark.init();

        Spark.options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        Spark.get("/", (request, response) -> "click <a href=\"" + SpotifyApi.getAuthCodeURI() + "\">here</a> to authenticate.");

        Spark.get("/callback", (request, response) -> {
            if(request.queryParams("code") == null){
                response.redirect("/");
                return "redirecting...";
            }

            SpotifyApi.authenticate(request.queryParams("code"));
            response.status(200);
            return "authenticated.";
        });

        // media controls
        Spark.get("/skip", (request, response) -> {
            SpotifyApi.playNext();
            return 200;
        });

        Spark.get("/previous", (request, response) -> {
            SpotifyApi.playPrevious();
            return 200;
        });

        Spark.get("/playpause", (request, response) -> {
            SpotifyApi.setPlaying(!SpotifyState.isPlaying);
            SpotifyState.isPlaying = !SpotifyState.isPlaying;
            return 200;
        });

        // currently playing (look at class)
        Spark.get("currently_playing", (request, response) -> {
            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("current_progress_ms", SpotifyState.currentProgressMs);
            responseObject.addProperty("duration_ms", SpotifyState.songDuration);
            responseObject.addProperty("song_name", SpotifyState.songName);
            responseObject.addProperty("artist_name", SpotifyState.artistName);
            responseObject.addProperty("album_cover_url", SpotifyState.albumcover);

            response.status(200);
            return responseObject.toString();
        });

        // change volume
        Spark.post("/volume", ((request, response) -> {
            String jsonData = request.body();

            Gson gson = new Gson();
            VolumeRequest pojo = gson.fromJson(jsonData, VolumeRequest.class);

            SpotifyApi.setVolume(pojo.volume);
            SpotifyState.volumePercent = pojo.volume;

            response.status(200);;
            return 200;
        }));

        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        DisplayController.state = ViewState.AWAITING_AUTHENTICATION;
    }
}
