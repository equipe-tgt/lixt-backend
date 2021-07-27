package br.com.ifsp.pi.lixt.data.business.purchaselist;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchase;
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
@Table(name = "tb_purchase_list")
public class PurchaseList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_purchase_list", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "id_purchase", nullable = false)
	private Long purchaseId;
	
	@Column(name = "id_list")
	private Long listId;
	
	@Column(name = "nr_partial_purchase_price")
	private BigDecimal partialPurchasePrice;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_purchase_list", insertable = false, updatable = false)
	private List<ItemOfPurchase> itemsOfPurchase;
	
}
