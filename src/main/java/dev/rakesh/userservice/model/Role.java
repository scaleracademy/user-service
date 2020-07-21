package dev.rakesh.userservice.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToMany
	@JoinTable(name = "role_permission", inverseJoinColumns = @JoinColumn(name = "perm_id", referencedColumnName = "id"),
											joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Permission> permissions;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<User> users;
 }
