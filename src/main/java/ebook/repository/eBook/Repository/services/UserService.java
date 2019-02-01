package ebook.repository.eBook.Repository.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ebook.repository.eBook.Repository.pojo.User;
import ebook.repository.eBook.Repository.pojo.UserDetailsImpl;

@Service
public class UserService implements IUserService {

	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public List<User> get() {
		EntityManager em = emf.createEntityManager();
		List<User> res = em.createQuery("FROM User", User.class).getResultList();
		em.close();
		return res;
	}

	@Override
	public User get(Serializable userName) {
		EntityManager em = emf.createEntityManager();
		User res =  em.createQuery("FROM User where username = :username", User.class).setParameter("username", userName)
				.getResultList().get(0);
		em.close();
		return res;
		// return em.find(User.class, userName);
	}

	@Override
	public User add(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		User res = user;
		em.close();
		return res;
	}

	@Override 
	public User update(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User savedUser = em.merge(user);
		em.getTransaction().commit();
		em.close();
		return savedUser;
		
	}

	@Override
	public boolean delete(Serializable userName) {
		EntityManager em = emf.createEntityManager();
		
		User u = this.get(userName);
		em.getTransaction().begin();
		em.remove(em.contains(u) ? u : em.merge(u));
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public boolean login(String username, String password) {
		User u = this.get(username);
		if (u.getUserPassword().equals(password)) {

			UserDetailsImpl userD = new UserDetailsImpl(u);
			List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
			list.add(new SimpleGrantedAuthority(u.getType()));
			Authentication authentication = new UsernamePasswordAuthenticationToken(userD, null,
					userD.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			return true;
		} else
			return false;
	}

	@Override
	public boolean logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return true;
	}


	@Override 
	public boolean changePassword(String username, String password) {
		User u = this.get(username);
		u.setUserPassword(password);
		if (this.update(u) != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
