package br.com.ifsp.pi.lixt.data.business.productoflist;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
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
@Table(name = "tb_product_of_list", indexes = {
		@Index(columnList = "id_list", name = "fk_list_user"),
		@Index(columnList = "id_assigned_user", name = "fk_productOfList_userAssined"),
		@Index(columnList = "id_product", name = "fk_productOfList_product"),
		@Index(columnList = "id_user_who_marked", name = "fk_productOfList_userMarked"),
		@Index(columnList = "st_name", name = "idx_name")
})
public class ProductOfList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_product_of_list", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "id_product", nullable = false)
	private Long productId;
	
	@Column(name = "id_list", updatable = false, nullable = false)
	private Long listId;
	
	@Column(name = "id_assigned_user")
	private Long assignedUserId;
	
	@Column(name = "id_user_who_marked")
	private Long userWhoMarkedId;
	
	@Column(name = "st_name", nullable = false)
	private String name;
	
	@Column(name = "bl_is_marked")
	@Builder.Default
	private Boolean isMarked = false;

	@Column(name = "nr_amount")
	private Integer plannedAmount;
	
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
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_product_of_list", insertable = false, updatable = false)
	private List<Comment> comments;

}
