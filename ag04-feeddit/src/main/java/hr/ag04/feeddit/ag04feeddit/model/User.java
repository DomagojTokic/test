package hr.ag04.feeddit.ag04feeddit.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	private String password;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			joinColumns = {@JoinColumn(name = "fk_user")},
			inverseJoinColumns = {@JoinColumn(name = "fk_role")})
	private Set<Role> roles;
	
	public User() {
	}
	
	public User(User user) {
		this.roles = user.getRoles();
		this.name = user.getName();
		this.id = user.getId();
		this.password = user.getPassword();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
