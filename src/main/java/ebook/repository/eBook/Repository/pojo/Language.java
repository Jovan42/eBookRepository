package ebook.repository.eBook.Repository.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Language")
public class Language {
	@Id
    @GeneratedValue
    private int id;
	@Column(columnDefinition = "NVARCHAR(30)")
	private String name;
	
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
	}
}
