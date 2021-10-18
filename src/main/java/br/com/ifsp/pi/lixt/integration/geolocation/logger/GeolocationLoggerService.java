package br.com.ifsp.pi.lixt.integration.geolocation.logger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationLoggerService {

    private final GeolocationLoggerRepository geolocationLoggerRepository;

    public void increaseCounter(Long increaseAmount){
        List<GeolocationLogger> all = this.geolocationLoggerRepository.findAll();

        if(all.isEmpty()){
            GeolocationLogger geolocationLogger = new GeolocationLogger();
            geolocationLogger.setTotalCount(increaseAmount);
            this.geolocationLoggerRepository.save(geolocationLogger);
            return;
        }

        GeolocationLogger geolocationLogger = new GeolocationLogger();
        geolocationLogger = all.get(0);
        geolocationLogger.setTotalCount(all.get(0).getTotalCount()+increaseAmount);
        this.geolocationLoggerRepository.save(geolocationLogger);
    }

    public Long getTotalCount() {
        List<GeolocationLogger> all = this.geolocationLoggerRepository.findAll();

        if(all.isEmpty())
            return Long.valueOf(0);

        return all.get(0).getTotalCount();
    }
}
