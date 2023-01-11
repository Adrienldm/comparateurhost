package com.mycompany.myapp.batch;

import com.mycompany.myapp.config.AsyncConfiguration;
import com.mycompany.myapp.domain.Serveur;
import java.util.Set;
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

    private static final String NETWORK = "network";
    private static final String HOURLY_PRICE = "hourly_price";
    private static final String MONTHLY_PRICE = "monthly_price";
    private static final String VOLUMES_CONSTRAINT = "volumes_constraint";
    private static final String TYPE = "type";
    private static final String MAX_SIZE = "max_size";
    private static final String RAM = "ram";
    private static final String NCPUS = "ncpus";
    private static final String ARCH = "arch";
    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Scheduled(cron = "10 * * * * *")
    public void execute() throws ParseException {
        log.info("--------------Debut du traitement---------------");

        String uri = "https://api.scaleway.com/instance/v1/zones/fr-par-1/products/servers";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);
        json = (JSONObject) json.get("servers");
        Set<String> set = json.keySet();
        for (String setStr : set) {
            JSONObject obj = (JSONObject) json.get(setStr);
            System.out.println(obj.get(NCPUS));
            Serveur serv = new Serveur();
            serv.setNomHebergeur("Scaleway");
            serv.setNomServeur(setStr);
            if (obj.containsKey(ARCH)) {
                serv.setArch((String) obj.get(ARCH));
            }
            if (obj.containsKey(NCPUS)) {
                serv.setCpuNombre((Long) obj.get(NCPUS));
            }
            if (obj.containsKey(RAM)) {
                serv.setRam((Long) obj.get(RAM));
            }
            if (obj.containsKey(VOLUMES_CONSTRAINT) && ((JSONObject) obj.get(VOLUMES_CONSTRAINT)).containsKey(MAX_SIZE)) {
                serv.setMaxSire((Long) ((JSONObject) obj.get(VOLUMES_CONSTRAINT)).get(MAX_SIZE));
            }
            if (obj.containsKey(TYPE)) {
                serv.setType((String) obj.get(TYPE));
            }
            if (obj.containsKey(MONTHLY_PRICE)) {
                serv.setPriceMonthly((Long) obj.get(MONTHLY_PRICE));
            }
            if (obj.containsKey(HOURLY_PRICE)) {
                serv.setHourlyPrice((Long) obj.get(HOURLY_PRICE));
            }

            System.out.println(serv.toString());
        }
        /*
        JSONArray servs = (JSONArray)json.get("servers");
        System.out.println(servs.get(1));
        */
    }
}
