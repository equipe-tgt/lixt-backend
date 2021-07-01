package br.com.ifsp.pi.lixt.data.business.product;

import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ifsp.pi.lixt.data.business.category.Category;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
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
@Table(name = "tb_product", indexes = {
		@Index(columnList = "id_user", name = "fk_product_user"),
		@Index(columnList = "id_category", name = "fk_product_category"),
		@Index(columnList = "st_name", name = "idx_product_name"),
		@Index(columnList = "st_barcode", name = "idx_product_barcode")
})
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_product", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "st_name", nullable = false)
	private String name;
	
	@Column(name = "id_user")
	private Long userId;
	
	@Column(name = "id_category", nullable = false)
	private Long categoryId;
	
	@Column(name = "st_barcode")
	private String barcode;
	
	@Column(name = "nr_measure_value")
	private BigDecimal measureValue;
	
	@Convert(converter = MeasureType.Converter.class)
	@Column(name = "en_measure_type")
	private MeasureType measureType;
	
	@ManyToOne
	@JoinColumn(name = "id_category", insertable = false, updatable = false)
	private Category category;

}
