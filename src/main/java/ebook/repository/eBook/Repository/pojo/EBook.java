package ebook.repository.eBook.Repository.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EBook")
public class EBook {
	@Id
	@GeneratedValue
	private int id;
	@Column(columnDefinition = "NVARCHAR(80)")
	private String title;
	@Column(columnDefinition = "NVARCHAR(120)")
	private String author;
	@Column(columnDefinition = "NVARCHAR(120)")
	private String keyWords;
	private int publicationyear;
	@Column(columnDefinition = "NVARCHAR(200)")
	private String filename;
	@Column(columnDefinition = "NVARCHAR(100)")
	private String MIME;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Ebook_Categories", joinColumns = { @JoinColumn(name = "ebook_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private List<Category> categories = new ArrayList<Category>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public int getPublicationyear() {
		return publicationyear;
	}

	public void setPublicationyear(int publicationyear) {
		this.publicationyear = publicationyear;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMIME() {
		return MIME;
	}

	public void setMIME(String mIME) {
		MIME = mIME;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
