package com.fernando.arribas.tfm.controller;

import com.fernando.arribas.tfm.api.SearchService;
import com.fernando.arribas.tfm.kafka.KafkaMessageProducer;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);


    @Autowired
    SearchService searchService;

    @ResponseBody
    @GetMapping("/date/{date}")
    public String getDataByDate(@PathVariable String date) throws ParseException {

        return searchService.getDataByDate(date);
    }

}
