package br.com.ifsp.pi.lixt.integration.barcode.v1.logger;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_barcode_logger", indexes = { @Index(columnList = "st_barcode", name = "fk_barcode_barcode_logger") })
public class BarcodeLogger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_barcode_logger", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "st_barcode")
	private String barcode;
	
	@Column(name = "nr_counter_of_day")
	private Long counterOfDay;
	
	@Builder.Default
	@Column(name = "id_product")
	private Long idProduct = null;
	
	@Column(name = "dt_created_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

}
