package br.com.ifsp.pi.lixt.data.business.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_user", updatable = false)
	@Builder.Default
	private Long id = null;

	@Column(name = "st_name")
	private String name;
	
	@Column(name = "st_username")
	private String username;

	@Column(name = "st_email", unique = true)
	private String email;

	@Column(name = "st_password")
	private String password;
	
	@Column(name = "st_first_access_token")
	private String firstAccessToken;

	@Builder.Default
	@Column(name = "bl_activated")
	private boolean activated = false;

} 