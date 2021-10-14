package br.com.ifsp.pi.lixt.integration.geolocation;

import br.com.ifsp.pi.lixt.integration.geolocation.data.FeatureCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    @Value("${lixt.geolocation.token}") String token;
    @Value("${lixt.geolocation.url}") String url;

    public FeatureCollection getGeocodingDataByCoordinates(Double latitude, Double longitude) {
        final String uri = url +
                latitude + ","+
                longitude + ".json" +
                "?types=poi&" +
                "access_token=" + token;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uri, FeatureCollection.class);
    }
}
