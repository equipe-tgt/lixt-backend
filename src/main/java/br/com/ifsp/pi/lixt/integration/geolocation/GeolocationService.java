package br.com.ifsp.pi.lixt.integration.geolocation;

import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.integration.geolocation.data.FeatureCollection;
import br.com.ifsp.pi.lixt.utils.exceptions.NotFoundException;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    @Value("${lixt.geolocation.token}") String token;
    @Value("${lixt.geolocation.url}") String url;

    private static final Logger logger = LoggerFactory.getLogger(SenderMail.class);

    public PurchaseLocalDto getPurchaseLocalByCoordinates(PurchaseLocalDto purchaseLocalDto) {

        if (purchaseLocalDto.getLatitude() < -90 || purchaseLocalDto.getLatitude() > 90)
        {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees inclusive.");
        }

        if (purchaseLocalDto.getLongitude() < -180 || purchaseLocalDto.getLongitude() > 180)
        {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees inclusive.");
        }

        final String uri = url +
                purchaseLocalDto.getLongitude() + ","+
                purchaseLocalDto.getLatitude() + ".json" +
                "?types=poi&" +
                "access_token=" + token;

        RestTemplate restTemplate = new RestTemplate();

        logger.info("Enviando requisição a API MapBox...");
        FeatureCollection featureCollection = restTemplate.getForObject(uri, FeatureCollection.class);
        logger.info("Requisição a API MapBox enviada.");

        if(featureCollection.getFeatures().length == 0){
            throw new NotFoundException("Não foi encontrado nenhum Ponto de interesse nas coordenadas enviadas.");
        }

        PurchaseLocalDto newPurchaseLocalDto = new PurchaseLocalDto();

        newPurchaseLocalDto.setLatitude(purchaseLocalDto.getLatitude());
        newPurchaseLocalDto.setLongitude(purchaseLocalDto.getLongitude());
        newPurchaseLocalDto.setName(featureCollection.getFeatures()[0].getPlace_name());

        return newPurchaseLocalDto;
    }
}
