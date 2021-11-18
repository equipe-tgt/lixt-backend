package br.com.ifsp.pi.lixt.data;

import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.integration.geolocation.GeolocationService;
import br.com.ifsp.pi.lixt.integration.geolocation.logger.GeolocationLoggerService;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar casos de integração da API de geolocalização")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeolocationIntegrationTest {

    @Autowired
    private GeolocationService geolocationService;

    @Autowired
    private GeolocationLoggerService geolocationLoggerService;

    @Test
    @DisplayName("Consultar a API com coordenadas inválidas")
    @Order(1)
    void invalidRequestToApi() {
        assertThrows(
                PreconditionFailedException.class,
                () -> this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(93.0).longitude(20.77777).build())
        );

        assertThrows(
                PreconditionFailedException.class,
                () -> this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(-93.0).longitude(20.77777).build())
        );

        assertThrows(
                PreconditionFailedException.class,
                () -> this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(23.66666).longitude(183.0).build())
        );

        assertThrows(
                PreconditionFailedException.class,
                () -> this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(23.66666).longitude(-183.0).build())
        );

        assertThrows(
                PreconditionFailedException.class,
                () -> this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(-93.0).longitude(-183.0).build())
        );

        assertThrows(
                PreconditionFailedException.class,
                () -> this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(183.0).longitude(93.0).build())
        );
    }

    @Test
    @DisplayName("Consultar a API com coordenadas válidas sem ponto de interesse")
    @Order(2)
    void requestToApiCoordinatesWithoutPOI() {
        assertThat(
                this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(24.2733562461887).longitude(16.36125626341889).build())
        ).isNull();
    }

    @Test
    @DisplayName("Consultar a API com coordenadas válidas de um ponto de interesse")
    @Order(3)
    void requestToApiCoordinatesWithPOI() {
        assertEquals(this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(-23.598575943903683).longitude(-46.64251755477454).build()).getName(),
                "Hirota Food Express, R. Pedro de Toledo, 591, São Paulo, São Paulo 04039, Brazil");
    }

    @Test
    @DisplayName("Efetuar requisição com limite maximo atingido")
    @Order(4)
    void exceedNumberOfMaximumRequests() {
        Long numberOfRequests = this.geolocationLoggerService.getTotalCount();
        this.geolocationLoggerService.increaseCounter((100000-numberOfRequests));

        assertThat(
                this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalDto.builder().latitude(-23.598575943903683).longitude(-46.64251755477454).build())
        ).isNull();

        this.geolocationLoggerService.increaseCounter(-(100000-numberOfRequests));
    }
}
