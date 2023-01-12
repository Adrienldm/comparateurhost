package com.mycompany.myapp.batch;

import com.mycompany.myapp.config.AsyncConfiguration;
import com.mycompany.myapp.domain.Serveur;
import com.mycompany.myapp.repository.ServeurRepository;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class BatchGetAllServeurs {

    private static final String BAREMETAL = "baremetal";
    private static final String SUM_INTERNET_BANDWIDTH = "sum_internet_bandwidth";
    private static final String SUM_INTERNAL_BANDWIDTH = "sum_internal_bandwidth";
    private static final String IPV6_SUPPORT = "ipv6_support";
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

    @Autowired
    private ServeurRepository serveurRepository;

    @Scheduled(cron = "10 * * * * *")
    public void execute() throws ParseException {
        serveurRepository.deleteAll();
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
                serv.setRam((Long) obj.get(RAM) / 1024 / 1024 / 1024);
            }
            if (obj.containsKey(VOLUMES_CONSTRAINT) && ((JSONObject) obj.get(VOLUMES_CONSTRAINT)).containsKey(MAX_SIZE)) {
                serv.setMaxSize((Long) ((JSONObject) obj.get(VOLUMES_CONSTRAINT)).get(MAX_SIZE));
            }
            if (obj.containsKey(BAREMETAL) && ((Boolean) obj.get(BAREMETAL))) {
                serv.setType(BAREMETAL);
            }

            if (obj.containsKey(MONTHLY_PRICE)) {
                serv.setPriceMonthly((Double) obj.get(MONTHLY_PRICE));
            }
            if (obj.containsKey(HOURLY_PRICE)) {
                serv.setHourlyPrice((Double) obj.get(HOURLY_PRICE));
            }

            if (obj.containsKey(NETWORK)) {
                if (((JSONObject) obj.get(NETWORK)).containsKey(IPV6_SUPPORT)) {
                    serv.setIpv6((Boolean) ((JSONObject) obj.get(NETWORK)).get(IPV6_SUPPORT));
                }
                if (((JSONObject) obj.get(NETWORK)).containsKey(SUM_INTERNAL_BANDWIDTH)) {
                    serv.setBandWidthInternal((Long) ((JSONObject) obj.get(NETWORK)).get(SUM_INTERNAL_BANDWIDTH));
                }
                if (((JSONObject) obj.get(NETWORK)).containsKey(SUM_INTERNET_BANDWIDTH)) {
                    serv.setBandWidthExternal((Long) ((JSONObject) obj.get(NETWORK)).get(SUM_INTERNET_BANDWIDTH));
                }
            }
            serveurRepository.save(serv);
        }
    }
}
