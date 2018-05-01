package hr.ag04.feeddit.ag04feeddit.service;

import hr.ag04.feeddit.ag04feeddit.model.CustomUserDetails;
import hr.ag04.feeddit.ag04feeddit.model.User;
import hr.ag04.feeddit.ag04feeddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service which handles user login.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByName(username);
		
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return optionalUser.map(CustomUserDetails::new).get();
	}
}
