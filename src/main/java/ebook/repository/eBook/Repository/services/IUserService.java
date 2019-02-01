package ebook.repository.eBook.Repository.services;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import ebook.repository.eBook.Repository.pojo.User;

public interface IUserService extends IGenericService<User, Serializable>{
	@Transactional
	 boolean login(String username, String password);
	@Transactional
	boolean changePassword(String username, String password);
	boolean logout();

}
