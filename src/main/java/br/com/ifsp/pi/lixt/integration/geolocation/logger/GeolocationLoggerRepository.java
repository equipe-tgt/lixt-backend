package br.com.ifsp.pi.lixt.integration.geolocation.logger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeolocationLoggerRepository extends JpaRepository<GeolocationLogger, Long> {
}
