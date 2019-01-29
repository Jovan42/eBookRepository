package ebook.repository.eBook.Repository.services;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import ebook.repository.eBook.Repository.pojo.EBook;

@Service
public class EBookService implements IeBookService {
	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<EBook> get() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("FROM EBook", EBook.class).getResultList();
	}

	@Override
	public EBook get(Serializable id) {
		EntityManager em = emf.createEntityManager();
		return em.find(EBook.class, id);
	}

	@Override
	public EBook add(EBook eBook) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(eBook);
		em.getTransaction().commit();
		return eBook;
	}

	@Override
	public EBook update(EBook eBook) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		EBook savedEBook = em.merge(eBook);
		em.getTransaction().commit();
		return savedEBook;
	}

	@Override
	public boolean delete(Serializable id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(EBook.class, id));
		em.getTransaction().commit();
		return true;
	}

}
