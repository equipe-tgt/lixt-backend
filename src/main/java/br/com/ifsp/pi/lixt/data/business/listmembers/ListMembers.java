package br.com.ifsp.pi.lixt.data.business.listmembers;

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

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;
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
@Table(name = "tb_list_members", indexes = { 
		@Index(columnList = "id_list", name = "fk_listMembers_list"),
		@Index(columnList = "id_user", name = "fk_listMembers_user"),
})
public class ListMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_list_members", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;	

	@Column(name = "id_user", nullable = false, updatable = false)
	private Long userId;
	
	@Column(name = "id_list", nullable = false, updatable = false)
	private Long listId;
	
	@Convert(converter = StatusListMember.Converter.class)
	@Column(name = "en_status")
	private StatusListMember statusListMember;
	
	@ManyToOne
	@JoinColumn(name = "id_user", insertable = false, updatable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_list", insertable = false, updatable = false)
	private ListOfItems list;

}
