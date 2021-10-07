package br.com.ifsp.pi.lixt.data.business.globalcomment;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "tb_global_comment", indexes = {
		@Index(columnList = "id_product", name = "fk_globalComment_product"),
		@Index(columnList = "id_user", name = "fk_globalComment_user")
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

	@Column(name = "id_product", nullable = false, updatable = false)
	private Long productId;
}
