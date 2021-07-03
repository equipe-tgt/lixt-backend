package br.com.ifsp.pi.lixt.data.business.list;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfList;
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
@Table(name = "tb_list", indexes = { @Index(columnList = "id_user", name = "fk_list_user") })
public class ListOfItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_list", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "st_name_list", nullable = false)
	private String nameList;
	
	@Column(name = "id_user", nullable = false, updatable = false)
	private Long ownerId;
	
	@Column(name = "st_description", nullable = false)
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_list", insertable = false, updatable = false)
	private List<ProductOfList> productsOfList;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_list", insertable = false, updatable = false)
	private List<ListMembers> listMembers;

}
