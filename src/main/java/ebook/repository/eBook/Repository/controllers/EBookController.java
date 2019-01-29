package ebook.repository.eBook.Repository.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ebook.repository.eBook.Repository.pojo.EBook;
import ebook.repository.eBook.Repository.services.IeBookService;

@RestController
@RequestMapping(value = "/eBooks")
public class EBookController {
	private IeBookService eBookService;

	@Autowired
	public void setUserService(IeBookService eBookService) {
		this.eBookService = eBookService;
	}

	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public List<EBook> get() {
		return eBookService.get();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public EBook get(@PathVariable int id) {
		return eBookService.get(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public EBook post(@RequestBody EBook eBook) {
		return eBookService.add(eBook);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public EBook put(@PathVariable int id, @RequestBody EBook eBook) {
		eBook.setId(id);
		return eBookService.update(eBook);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable int id) {
		return eBookService.delete(id);
	}
}
