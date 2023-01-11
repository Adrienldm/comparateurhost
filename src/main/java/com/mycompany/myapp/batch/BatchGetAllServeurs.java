package com.mycompany.myapp.batch;

import com.mycompany.myapp.config.AsyncConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class BatchGetAllServeurs {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Scheduled(cron = "10 * * * * *")
    public void execute() throws ParseException {
        log.info("--------------Debut du traitement---------------");

        String uri = "https://api.scaleway.com/instance/v1/zones/fr-par-1/products/servers";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);
        System.out.println(json.get("servers").toString());
        // System.out.println(result);
    }
}
