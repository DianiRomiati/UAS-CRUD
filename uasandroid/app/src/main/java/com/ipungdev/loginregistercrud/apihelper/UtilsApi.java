package com.ipungdev.loginregistercrud.apihelper;

public class UtilsApi {
    // http://127.0.0.1:8000 ini adalah localhost.
    public static final String BASE_URL_API = "https://api.nativemed.id/public/api/";

    // Mendeklarasikan Interface BaseApiService
    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL_API).create(UserService.class);
    }

}
