package br.com.ifsp.pi.lixt.data.business.globalcomment;

import br.com.ifsp.pi.lixt.data.business.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "tb_global_comment", indexes = {
		@Index(columnList = "id_product", name = "fk_globalComment_product"),
		@Index(columnList = "id_user", name = "fk_globalComment_user"),
		@Index(columnList = "dt_created_at", name = "idx_itemOfPurchase_createdAt"),
})
public class GlobalComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_global_comment", updatable = false, nullable = false)
	@Builder.Default
	private Long id = null;

	@Column(name = "st_content")
	private String content;

	@Column(name = "id_user", updatable = false)
	private Long userId;

	@Column(name = "dt_created_at")
	private LocalDateTime date;

	@Column(name = "id_product", nullable = false, updatable = false)
	private Long productId;

	@Column(name = "bool_public", nullable = false, updatable = true)
	private Boolean isPublic;

	@ManyToOne
	@JoinColumn(name = "id_user", insertable = false, updatable = false)
	private User user;
}
