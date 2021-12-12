package br.com.ifsp.pi.lixt.data.business.comment;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ifsp.pi.lixt.data.business.user.User;
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
@Table(name = "tb_comment", indexes = {
		@Index(columnList = "id_product_of_list", name = "fk_comment_productOfList"),
		@Index(columnList = "id_user", name = "fk_comment_user"),
		@Index(columnList = "dt_created_at", name = "idx_itemOfPurchase_createdAt"),
})
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_comment", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;
	
	@Column(name = "id_user", updatable = false)
	private Long userId;
	
	@Column(name = "id_product_of_list")
	private Long productOfListId;
	
	@Column(name = "st_content")
	private String content;
	
	@Column(name = "dt_created_at")
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "id_user", insertable = false, updatable = false)
	private User user;

}
