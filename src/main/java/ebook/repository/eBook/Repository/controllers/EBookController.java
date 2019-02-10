package ebook.repository.eBook.Repository.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ebook.repository.eBook.Repository.lucene.Indexer;
import ebook.repository.eBook.Repository.lucene.model.IndexUnit;
import ebook.repository.eBook.Repository.lucene.model.RequiredHighlight;
import ebook.repository.eBook.Repository.lucene.model.SimpleQuery;
import ebook.repository.eBook.Repository.lucene.search.QueryBuilder;
import ebook.repository.eBook.Repository.lucene.search.ResultRetriever;
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
	public boolean upload(@RequestParam("file") MultipartFile file,
						  @RequestParam("title") String title,
						  @RequestParam("keywords") String keywords) {
		try {
			
           
		byte[] bytes = file.getBytes();
		Path path = Paths.get("C:\\a\\" + file.getOriginalFilename());
		Files.write(path, bytes);
		indexUploadedFile("C:\\a\\" + file.getOriginalFilename(), title, keywords);
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

	@RequestMapping(value = "/searchTitle/{term}", method = RequestMethod.GET, produces = "application/json")
	public List<EBook> searchTitle(@PathVariable String term) throws Exception {
		List<EBook> ebookList = new ArrayList<>();
		for (String filename : search("title", term)) {
			ebookList.add(eBookService.getByFilename(filename));	
		} 
		return ebookList;
	}

	@RequestMapping(value = "/searchText/{term}", method = RequestMethod.GET, produces = "application/json")
	public List<EBook> searchText(@PathVariable String term) throws Exception {
		List<EBook> ebookList = new ArrayList<>();
		for (String filename : search("text", term)) {
			ebookList.add(eBookService.getByFilename(filename));	
		} 
		return ebookList;
	}
	public List<String> search(String field, String term) throws Exception {
		SimpleQuery simpleQuery = new SimpleQuery();
		simpleQuery.setField(field);
		simpleQuery.setValue(term);
		
		Query query= QueryBuilder.buildQuery(simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<String> results = ResultRetriever.getResults(query, rh);			
		System.out.println(results.size());
		return results;
	}
	
	private void indexUploadedFile(String fileName, String title, String keywords) throws IOException{

		if(fileName != null){
			IndexUnit indexUnit = Indexer.getInstance().getHandler(fileName).getIndexUnit(new File(fileName));
			indexUnit.setTitle(title);
			indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(keywords.split(" "))));
			Indexer.getInstance().add(indexUnit.getLuceneDocument());
		}
		
	}
}
