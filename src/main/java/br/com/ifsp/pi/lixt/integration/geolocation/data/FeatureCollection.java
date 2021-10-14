package br.com.ifsp.pi.lixt.integration.geolocation.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeatureCollection {
    private String type;
    private Query[] query;
    private Feature[] features;
    private String attribution;
}
