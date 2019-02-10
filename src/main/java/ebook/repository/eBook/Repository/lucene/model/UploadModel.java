package ebook.repository.eBook.Repository.lucene.model;


import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class UploadModel {

    private String title;
    
    private String keywords;

    private MultipartFile file;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public MultipartFile getFile() {
        return file;
    }

    public void setFiles(MultipartFile files) {
        this.file = files;
    }

}
