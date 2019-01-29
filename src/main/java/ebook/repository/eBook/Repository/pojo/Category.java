package ebook.repository.eBook.Repository.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Category")
public class Category {
	@Id
	@GeneratedValue
	private int id;
	@Column(columnDefinition = "NVARCHAR(30)")
	private String name;
	
	@ManyToMany(mappedBy = "categories")
	private List<EBook> eBooks = new ArrayList<EBook>();
	
	@JsonIgnore
	@ManyToMany(mappedBy = "categories")
	private List<User> users = new ArrayList<User>();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EBook> geteBooks() {
		return eBooks;
	}

	public void seteBooks(List<EBook> eBooks) {
		this.eBooks = eBooks;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	

}
