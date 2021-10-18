package br.com.ifsp.pi.lixt.integration.geolocation.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Context {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String wikidata;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String short_code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String text;
}
