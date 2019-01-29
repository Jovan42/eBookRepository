package ebook.repository.eBook.Repository.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private int id;
	@Column(columnDefinition = "NVARCHAR(30)")
	private String firstname;
	@Column(columnDefinition = "NVARCHAR(30)")
	private String lastname;
	@Column(columnDefinition = "NVARCHAR(10)", unique = true, nullable = false)
	private String username;
	@Column(columnDefinition = "NVARCHAR(10)")
	private String password;
	@Column(columnDefinition = "NVARCHAR(30)")
	private String type;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "user_categories", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private List<Category> categories = new ArrayList<Category>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return password;
	}

	public void setUserPassword(String userPassword) {
		this.password = userPassword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
