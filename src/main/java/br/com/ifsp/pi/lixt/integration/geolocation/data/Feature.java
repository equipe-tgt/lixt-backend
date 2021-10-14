package br.com.ifsp.pi.lixt.integration.geolocation.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feature {

    private String id;

    private String type;

    private String[] place_type;

    private Float relevance;

    private Properties properties;

    private String text;

    private String place_name;

    private Query[] center;

    private Context[] context;
}
