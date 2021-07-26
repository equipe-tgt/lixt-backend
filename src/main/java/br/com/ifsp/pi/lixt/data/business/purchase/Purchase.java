package br.com.ifsp.pi.lixt.data.business.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchase;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocal;
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
@Table(name = "tb_purchase", indexes = { 
		@Index(columnList = "id_user", name = "fk_purchase_user"),
		@Index(columnList = "id_list", name = "fk_purchase_list"),
		@Index(columnList = "id_purchase_local", name = "fk_purchase_groceryLocal"),
})
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_purchase", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "id_user", nullable = false)
	private Long userId;
	
	@Column(name = "id_list", nullable = false)
	private Long listId;
	
	@Column(name = "id_purchase_local", nullable = false)
	private Long purchaseLocalId;
	
	@Column(name = "nr_purchase_price")
	private BigDecimal purchasePrice;
	
	@Column(name = "dt_purchase_date")
	private LocalDateTime purchaseDate;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_purchase", insertable = false, updatable = false)
	private List<ItemOfPurchase> itemsOfPurchase;
	
	@ManyToOne
	@JoinColumn(name = "id_purchase_local", insertable = false, updatable = false)
	private PurchaseLocal purchaseLocal;
	
	@ManyToOne
	@JoinColumn(name = "id_list", insertable = false, updatable = false)
	private ListOfItems list;

}
