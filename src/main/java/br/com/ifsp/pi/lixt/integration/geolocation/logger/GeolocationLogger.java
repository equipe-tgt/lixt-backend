package br.com.ifsp.pi.lixt.integration.geolocation.logger;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "tb_geolocation_logger")
public class GeolocationLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_geolocation_logger", updatable = false, nullable = false)
    private Long id = null;

    @Column(name = "nr_total_count")
    private Long totalCount;

}
