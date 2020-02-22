package com.example.nearby.retrofit;

public class Common {
    private static final String GOOGLE_API_URl="https://maps.googleapis.com/maps/api";

    private static IGoogleAPI getGoogleAPIService() {
        return RetrofitClient.getClient(GOOGLE_API_URl).create(IGoogleAPI.class);
    }
}
