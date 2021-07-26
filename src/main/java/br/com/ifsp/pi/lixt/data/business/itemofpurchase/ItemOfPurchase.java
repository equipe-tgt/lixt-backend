package br.com.ifsp.pi.lixt.data.business.itemofpurchase;

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

import br.com.ifsp.pi.lixt.data.business.product.Product;
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
@Table(name = "tb_item_of_purchase", indexes = { 
		@Index(columnList = "id_purchase", name = "fk_itemOfPurchase_purchase"),
		@Index(columnList = "id_product", name = "fk_itemOfPurchase_product"),
		@Index(columnList = "st_name", name = "idx_itemOfPurchase_name"),
})
public class ItemOfPurchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_item_of_purchase", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "id_purchase")
	private Long purchaseId;
	
	@Column(name = "id_product", nullable = false)
	private Long productId;
	
	@Column(name = "st_name", nullable = false)
	private String name;
	
	@Column(name = "nr_amount")
	private Integer amount;
	
	@Column(name = "nr_price")
	private BigDecimal price;
	
	@Column(name = "nr_measure_value")
	private BigDecimal measureValue;
	
	@Convert(converter = MeasureType.Converter.class)
	@Column(name = "en_measure_type", nullable = false)
	private MeasureType measureType;
	
	@ManyToOne
	@JoinColumn(name = "id_product", insertable = false, updatable = false)
	private Product product;

}
