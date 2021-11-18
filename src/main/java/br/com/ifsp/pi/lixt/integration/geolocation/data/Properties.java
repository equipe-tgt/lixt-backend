package br.com.ifsp.pi.lixt.integration.geolocation.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Properties {

    private String foursquare;
    private Boolean landmark;
    private String address;
    private String category;

}
