package com.tsarit.form_1.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tsarit.form_1.model.userData;
import com.tsarit.form_1.repository.userRepository;


@Service
public class coustomUserDetailService implements UserDetailsService {
  
	@Autowired
	  private userRepository repo;
	
	
	  @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        userData user = repo.findByemailid(email);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	        }
//	        return new org.springframework.security.core.userdetails.User(user.getEmailid(), user.getPassword(), new ArrayList<>());
	        return user;
	    }
}
	
//	@Override
//	public UserDetails loadUserByUsername(String emailid) throws UsernameNotFoundException {
//		try {
//                userData u=repo.findByemailid(emailid);
//                if(u==null) {
//                	throw new UsernameNotFoundException("no user");
//                }else {
//                	return new coustomUserdetails(u);
//                }
//			
//		} catch (Exception e) {
//               e.printStackTrace();
//		}
//
//		return null;
		
	
//		userData user = repo.findByemailid(emailid);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found");
//		}
//		return new org.springframework.security.core.userdetails.User(user.getEmailid(), user.getPassword(),
//				getAuthorities(user));
//		return user;
//	}

//	private Collection<? extends GrantedAuthority> getAuthorities(userData user) {
//		return Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
//	}
//	}



