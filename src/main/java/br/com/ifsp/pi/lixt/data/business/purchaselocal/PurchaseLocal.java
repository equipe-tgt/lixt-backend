package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.locationtech.jts.geom.Point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "tb_purchase_local", indexes = {
		@Index(columnList = "point_location", name = "fk_purchaseLocal_local")
})
public class PurchaseLocal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_purchase_local", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "st_name", updatable = false, nullable = false)
	private String name;
	
	@Column(name = "point_location", nullable = false, columnDefinition = "Point")
	private Point location;
	
}
