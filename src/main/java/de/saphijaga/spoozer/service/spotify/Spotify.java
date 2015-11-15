package de.saphijaga.spoozer.service.spotify;

/**
 * Created by samuel on 12.11.15.
 */
public class Spotify {
    public static final String CLIENT_ID = "65650c3edf994b7ba844f88fd05c2d6c";
    public static final String CLIENT_SECRET = "";
    public static final String LOGIN_URL = "https://accounts.spotify.com/authorize";
    public static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    public static final String API_URL = "https://api.spotify.com/v1";
    public static final String REDIRECT_URL_PATH = "/spotify/callback";
    public static final String[] SCOPES = {"user-read-private", "playlist-read-private", "playlist-modify-private"};
    public static final String STATE = "spotify-state";
}