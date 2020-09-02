package com.fernando.arribas.tfm.utils;

import com.fernando.arribas.tfm.constants.Constants;
import com.fernando.arribas.tfm.httpUtils.HttpUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Utils {
    public static JSONObject parseCountriesTotalData(String date_today, String data) throws ParseException {
        JSONObject data_json = (JSONObject)new JSONParser().parse(data);
        JSONObject dates = (JSONObject) data_json.get("dates");
        JSONObject today = (JSONObject) dates.get(date_today);
        JSONObject countries = (JSONObject) today.get("countries");
        return countries;
    }

    public static List<String> getListCountries() throws ParseException {

        String data = HttpUtils.getCountries();

        JSONObject json = (JSONObject)new JSONParser().parse(data);
        JSONArray countries = (JSONArray) json.get("countries");

        List<String> countries_api = new ArrayList<String>();

        Iterator<JSONObject> iterator = countries.iterator();
        while(iterator.hasNext()) {
            JSONObject entry =  iterator.next();
            countries_api.add((String) entry.get("name"));
        }
        return countries_api;
    }

    public static List<JSONObject> getRegionsByCountry(JSONObject countries, String country, JSONObject totalData) throws ParseException {
        JSONObject data_country = (JSONObject) countries.get(country);
        JSONArray regions = (JSONArray) data_country.get("regions");


        List<JSONObject> regionsObjects = new ArrayList<JSONObject>();

        if(regions.isEmpty()){
            JSONObject region = new JSONObject();
            for(String field : Constants.fields){
                region.put(field,data_country.get(field));
            }
            region.put("CountryName",data_country.get("name"));
            region.put("RegionName",data_country.get("name"));
            region.put("total",totalData);
            regionsObjects.add(region);
        }else{
            Iterator<JSONObject> iterator = regions.iterator();
            while(iterator.hasNext()) {
                JSONObject entry =  iterator.next();
                JSONObject region = new JSONObject();
                for(String field : Constants.fields){
                    region.put(field,entry.get(field));
                }
                region.put("CountryName",data_country.get("name"));
                region.put("RegionName",entry.get("name"));
                region.put("total",totalData);
                regionsObjects.add(region);
            }
        }

        return regionsObjects;
    }


    public static JSONObject parseTotalData(String data) throws ParseException {
        JSONObject data_json = (JSONObject)new JSONParser().parse(data);
        JSONObject totalData = (JSONObject) data_json.get("total");
        return totalData;
    }

    public static JSONObject createCountryInfoMessage(JSONObject region) {
        SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        timestamp.setTimeZone(TimeZone.getTimeZone("UTC"));
        region.put("timestamp", timestamp.format(new Date()));
        return region;
    }
}
