package br.com.ifsp.pi.lixt.integration.geolocation;

import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.integration.geolocation.data.FeatureCollection;
import br.com.ifsp.pi.lixt.integration.geolocation.logger.GeolocationLoggerService;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    @Value("${lixt.geolocation.token}") String token;
    @Value("${lixt.geolocation.url}") String url;

    private static final Logger logger = LoggerFactory.getLogger(SenderMail.class);
    private static final Long MAX_AMOUNT_REQUEST = Long.parseLong("100000");
    private final GeolocationLoggerService geolocationLoggerService;

    public PurchaseLocalDto getPurchaseLocalByCoordinates(PurchaseLocalDto purchaseLocalDto) {

        if (purchaseLocalDto.getLatitude() < -90 || purchaseLocalDto.getLatitude() > 90)
        {
            throw new PreconditionFailedException("Eixos inválidos");
        }

        if (purchaseLocalDto.getLongitude() < -180 || purchaseLocalDto.getLongitude() > 180)
        {
            throw new PreconditionFailedException("Eixos inválidos");
        }

        final String uri = url +
                purchaseLocalDto.getLongitude() + ","+
                purchaseLocalDto.getLatitude() + ".json" +
                "?types=poi&" +
                "access_token=" + token;

        RestTemplate restTemplate = new RestTemplate();

        if(this.geolocationLoggerService.getTotalCount() < MAX_AMOUNT_REQUEST){
            logger.info("Enviando requisição a API MapBox...");
            FeatureCollection featureCollection = restTemplate.getForObject(uri, FeatureCollection.class);
            logger.info("Requisição a API MapBox enviada.");

            this.geolocationLoggerService.increaseCounter(1L);

            if(featureCollection.getFeatures().length == 0){
                logger.error("Não foi encontrado nenhum ponto de interesse nas coordenadas [latitude: " +
                        purchaseLocalDto.getLatitude() +
                        ", longitude: " + purchaseLocalDto.getLongitude() + "]");
                return null;
            }

            PurchaseLocalDto newPurchaseLocalDto = new PurchaseLocalDto();

            newPurchaseLocalDto.setLatitude(purchaseLocalDto.getLatitude());
            newPurchaseLocalDto.setLongitude(purchaseLocalDto.getLongitude());
            newPurchaseLocalDto.setName(featureCollection.getFeatures()[0].getPlace_name());

            return newPurchaseLocalDto;
        } else {
            logger.error("Não foi possível fazer requisição à API MapBox: número máximo de requisições atingido.");
            return null;
        }
    }
}
