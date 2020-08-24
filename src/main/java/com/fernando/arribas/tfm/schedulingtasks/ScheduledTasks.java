package com.fernando.arribas.tfm.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fernando.arribas.tfm.api.SearchService;
import com.fernando.arribas.tfm.controller.ApiController;
import com.fernando.arribas.tfm.httpUtils.HttpUtils;
import com.fernando.arribas.tfm.kafka.KafkaMessageProducer;
import com.fernando.arribas.tfm.utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    SearchService searchService;


    @Scheduled(fixedDelay = 3600000, initialDelay = 1000)
    public void callApi() throws ParseException {
        Date date = new Date();
        log.info("Get Data at {}", dateFormat.format(date));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date_today = formatter.format(date);

        String result = searchService.getDataByDate(date_today);
        log.info(result);
    }

}