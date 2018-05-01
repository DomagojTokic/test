package hr.ag04.feeddit.ag04feeddit.model;

import javax.persistence.*;

/**
 * User role used by Spring Security.
 */
@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "role")
	private String role;
	
	public Role() {
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
