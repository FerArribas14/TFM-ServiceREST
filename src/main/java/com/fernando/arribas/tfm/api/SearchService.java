package com.fernando.arribas.tfm.api;

import com.fernando.arribas.tfm.httpUtils.HttpUtils;
import com.fernando.arribas.tfm.kafka.KafkaMessageProducer;
import com.fernando.arribas.tfm.utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    KafkaMessageProducer producer;


    public String getDataByDate(String date) throws ParseException {

        try {
            List<String> countries_api = Utils.getListCountries();
            String data = HttpUtils.getCountriesTotalData(date);

            JSONObject countries = Utils.parseCountriesTotalData(date, data);
            JSONObject totalData = Utils.parseTotalData(data);

            for(String country :countries_api){
                List<JSONObject> regions = Utils.getRegionsByCountry(countries, country,totalData);
                for (JSONObject region : regions){
                    JSONObject output = Utils.createCountryInfoMessage(region);
                    producer.sendMessage(output.toJSONString());
                }
            }
            log.info("Send Data at {}", dateFormat.format(new Date()));
            return "Data get correctly";

        }catch (HttpClientErrorException e){
            return "Error getting data at response...\n" + e.getResponseBodyAsString();
        } catch(Exception e) {
            return "Error getting data\n" + e.getMessage();
        }
    }
}
