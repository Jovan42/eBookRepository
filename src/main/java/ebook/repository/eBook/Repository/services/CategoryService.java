package ebook.repository.eBook.Repository.services;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import ebook.repository.eBook.Repository.pojo.Category;



@Service
public class CategoryService implements ICategoryService {
	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
	@Override
	public List<Category> get() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("FROM Category", Category.class).getResultList();
	}

	@Override
	public Category get(Serializable id) {
		EntityManager em = emf.createEntityManager();
		return em.find(Category.class, id);
	}

	@Override
	public Category add(Category category) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(category);
		em.getTransaction().commit();
		return category;
	}

	@Override
	public Category update(Category category) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category savedCategory = em.merge(category);
		em.getTransaction().commit();
		return savedCategory;
	}

	@Override
	public boolean delete(Serializable id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Category.class, id));
		em.getTransaction().commit();
		return true;
	}
}
