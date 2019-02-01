package ebook.repository.eBook.Repository.services;

import java.io.Serializable;
import java.util.List;

import ebook.repository.eBook.Repository.pojo.EBook;

public interface IeBookService extends IGenericService<EBook, Serializable>{
    public List<EBook> getForCategoty(int id);
}
