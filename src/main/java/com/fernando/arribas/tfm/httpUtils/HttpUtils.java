package com.fernando.arribas.tfm.httpUtils;

import org.springframework.web.client.RestTemplate;

public class HttpUtils {

    private static final String Url = "https://api.covid19tracking.narrativa.com/api/";

    public static String getCountriesTotalData(String date_today) {
        RestTemplate restTemplate;

        final String request_url = Url + date_today;
        restTemplate = new RestTemplate();
        return restTemplate.getForObject(request_url, String.class);
    }

    public static String getCountries() {
        final String uri = Url + "countries";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
