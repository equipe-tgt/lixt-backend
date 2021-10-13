package br.com.ifsp.pi.lixt.integration.geolocation.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Features {

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String type;

    @JsonIgnore
    private String[] place_type;

    @JsonIgnore
    private Integer relevance;

    private Properties properties;
    private String text;
    private String place_name;

    @JsonIgnore
    private Query[] center;

    @JsonIgnore
    private String[] context;
}
