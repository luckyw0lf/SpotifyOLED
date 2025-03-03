# SpotifyOLED

A Raspberry Pi-based project that displays Spotify playback information on an OLED screen.

## Features
* Displays currently playing song, artist, and album.
* Uses an OLED display connected to a Raspberry Pi.
* Fetches Spotify playback data in real-time.
* Has media controlls and expandable menu support.
* A small api for getting song info localy.

## Requirements
* Raspberry Pi (any model with internet access)
* OLED Display (SSD1306/SH1106 compatible)
* Spotify API token (You can get this for free for personal use)
* Some buttons
* Wifi Access.

## Setup
1. Get your spotify app (api) credentials and update these in `SpotifyOLED.java` at line `30` eg.
2. Update the redirect url to your corresponding ip adress or hostname as shown in the example at line `30`
```java
  // set up the spotifyApi credentials
  String clientID = "3dbe240cdb61497da81646aed45c49c6";
  String clientSecret = "38f8ac867b3c466f9b222ffaf381b65c";
  String redirectURI = "http://rubenpi.local:8080/callback";
  String accessScope = "user-read-currently-playing user-read-playback-state user-modify-playback-state";
  SpotifyApi.setApiCredentials(clientID, clientSecret, redirectURI, accessScope);
```
3. Find the adress of your i2c oled display and update this in `SpotifyOLED.java` if needed at line `19`
```java
  // set up the display, and the controller behind it
  DisplayController displayController = new DisplayController(new OLEDDisplay(1, 0x3c));
```
4. Compile with Maven and transfer the `.jar` file. I've made a little target to build and scp the jar file to te raspberry pi, look in `pom.xml` for that.
5. Run the jar file and follow the instructions on the display!
