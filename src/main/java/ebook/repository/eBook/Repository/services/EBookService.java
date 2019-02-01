package ebook.repository.eBook.Repository.services;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebook.repository.eBook.Repository.pojo.EBook;

@Service
public class EBookService implements IeBookService {
	private EntityManagerFactory emf;

	
	@Autowired
	ICategoryService categoryService;

	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<EBook> getForCategoty(int id) {
		EntityManager em = emf.createEntityManager();
		List<EBook> res =  em.createQuery("FROM EBook where category_id = : category_id", EBook.class).setParameter("category_id", id)
				.getResultList();
		em.close();
		return res;
	}

	@Override
	public List<EBook> get() {
		EntityManager em = emf.createEntityManager();
		List<EBook> res = em.createQuery("FROM EBook", EBook.class).getResultList();
		em.close();
		return res;
	}

	@Override
	public EBook get(Serializable id) {
		EntityManager em = emf.createEntityManager();
		EBook res = em.find(EBook.class, id);
		em.close();
		return res;
	}

	@Override
	public EBook add(EBook eBook) {
		eBook.setCategory(categoryService.get(eBook.getCategory_id())); 
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(eBook);
		em.getTransaction().commit();
		em.close();
		return eBook;
	}

	@Override
	public EBook update(EBook eBook) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		EBook oldData = get(eBook.getId());
		oldData.setAuthor(eBook.getAuthor());
		oldData.setPublicationyear(eBook.getPublicationyear());
		oldData.setKeyWords(eBook.getKeyWords());
		oldData.setMIME(eBook.getMIME());

		oldData = em.merge(oldData);
		em.getTransaction().commit();
		em.close();
		return oldData;
	}

	@Override
	public boolean delete(Serializable id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(EBook.class, id));
		em.getTransaction().commit();
		em.close();
		return true;
	}

}
