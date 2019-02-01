package ebook.repository.eBook.Repository.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {
	@Id
	@GeneratedValue
	private int id;
	@Column(columnDefinition = "NVARCHAR(30)")
	private String name;

	/*@OneToMany(
		mappedBy="category",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.EAGER)*/
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private List<EBook> eBooks = new ArrayList<>();
	
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

}
