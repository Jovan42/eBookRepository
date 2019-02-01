package ebook.repository.eBook.Repository.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ebook.repository.eBook.Repository.pojo.Category;
import ebook.repository.eBook.Repository.services.ICategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	private ICategoryService categoryService;

	@Autowired
	public void setUserService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public List<Category> get() {
		return categoryService.get();
	}

	@PreAuthorize("hasAuthority('SUB')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public Category get(@PathVariable int id) {
		return categoryService.get(id);
	}

	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Category post(@RequestBody Category category) {
		return categoryService.add(category);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Category put(@PathVariable int id, @RequestBody Category category) {
		category.setId(id);
		return categoryService.update(category);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable int id) {
		return categoryService.delete(id);
	}

	
}
