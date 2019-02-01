package ebook.repository.eBook.Repository.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@RequestMapping(value = "/cat/{id}/", method = RequestMethod.GET, produces = "application/json")
	public List<EBook> getBooks(@PathVariable int id) {
		return eBookService.getForCategoty(id);
	}

	@PreAuthorize("hasAuthority('SUB')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public boolean upload(@RequestParam("file") MultipartFile file) {
		try {
			
           
		byte[] bytes = file.getBytes();
		Path path = Paths.get("C:\\a\\" + file.getOriginalFilename());
		Files.write(path, bytes);

		return true;

        } catch (IOException e) {
			e.printStackTrace();
           return false;
        }
	}

	@PreAuthorize("hasAuthority('SUB')")
	@RequestMapping(value = "/{id}/download", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<ByteArrayResource> download(@PathVariable int id) throws IOException {
		EBook ebook = get(id);
		Path path = Paths.get("C:\\a\\" + ebook.getFilename());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    	return ResponseEntity.ok().body(resource);
	}
	
}
