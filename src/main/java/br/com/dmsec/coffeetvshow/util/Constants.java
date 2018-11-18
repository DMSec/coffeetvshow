/*
 * coffeetvshow
 * Copyright (C) 2018  DMSec - @douglasmsi
 * 
 */


package br.com.dmsec.coffeetvshow.util;

public class Constants {
    public static final String TVST_CLIENT_ID = "PUT YOUR CLIENT_ID here. You need request to tvshowtime team";
    public static final String TVST_CLIENT_SECRET = "PUT YOUR CLIENT_ID here. You need request to tvshowtime team";
    public static final String TVST_USER_AGENT = "coffeetvshow";
    public static final String TVST_RATE_REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String TVST_AUTHORIZE_URI = "https://api.tvshowtime.com/v1/oauth/device/code";
    public static final String TVST_ACCESS_TOKEN_URI = "https://api.tvshowtime.com/v1/oauth/access_token";
    public static final String TVST_CHECKIN_URI = "https://api.tvshowtime.com/v1/checkin";
    public static final String TVST_TO_WATCH = "https://api.tvtime.com/v1/to_watch";
    public static final String TVST_SHOW = "https://api.tvtime.com/v1/show";
    public static final String TVST_LIBRARY = "https://api.tvtime.com/v1/library";
    public static final String TVST_USER = "https://api.tvtime.com/v1/user";
    public static final String OMDB_KEY ="YOU NEED GENERATE THE KEY from omdb api";
    public static final String OMDB_URI = "http://www.omdbapi.com/?apikey="+OMDB_KEY+"&";
    public static final String EZTV_URI = "https://eztv.ag/api/get-torrents?imdb_id=";

    public static final String PMS_WATCH_HISTORY = "/status/sessions/history/all";

    public static final Long SECOND_IN_MILIS = 1000L;
    public static final Long MINUTE_IN_MILIS = SECOND_IN_MILIS * 60;
}
