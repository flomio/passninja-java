package com.passninja;

public class Passninja {

    public static final String API_BASE_URL = "https://api.passninja.com/v1/";

    public static String accountId;
    public static String apiKey;

    public static void init(final String accountId, final String apiKey) {
        Passninja.accountId = accountId;
        Passninja.apiKey = apiKey;
    }

}
