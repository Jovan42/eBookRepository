package ebook.repository.eBook.Repository.pojo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	 private static final long serialVersionUID = 5197941260523577515L;

	    private User user;
	    

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public UserDetailsImpl(User user) {
	        this.user = user;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {

	        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("GUEST" ));
	        if(user.getType().equals("ADMIN")) {
	        	  authorities.add(new SimpleGrantedAuthority("SUB"));
	        	  authorities.add(new SimpleGrantedAuthority("ADMIN"));
	        }
	        if(user.getType().equals("SUB"))
	        	 authorities.add(new SimpleGrantedAuthority("SUB"));

	        return authorities;

	    }
	    
	    public boolean isAdmin() {
	    	return user.getType().equals("ADMIN");
	    }

	    @Override
	    public String getPassword() {
	        return user.getUserPassword();
	    }

	    @Override
	    public String getUsername() {
	        return user.getUsername();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
}
