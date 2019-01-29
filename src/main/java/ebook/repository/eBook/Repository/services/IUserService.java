package ebook.repository.eBook.Repository.services;

import java.io.Serializable;

import ebook.repository.eBook.Repository.pojo.User;

public interface IUserService extends IGenericService<User, Serializable>{
	 boolean login(String username, String password);
	 
	 boolean changePassword(String username, String password);
}
