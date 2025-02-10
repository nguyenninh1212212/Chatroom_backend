package app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.dto.user.AuthDTO;
import app.dto.user.ReqUserDTO;
import app.dto.user.UserDTO;
import app.dto.user.UserInfoDTO;
import app.entity.User;
import app.exception.UserAlreadyExistsException;
import app.repository.UserReponsitory;

@Service
public class UserService implements UserDetailsService {
    @Autowired
	private UserReponsitory userRep;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


 	
    public UserInfoDTO Register(UserDTO req) {
    	Optional<User> existingUser = userRep.findByUsernameAndEmail(req.getUsername(),req.getEmail());
    	if(existingUser.isPresent()) {
    		throw new UserAlreadyExistsException("Username or Email already exists!");
    	}
    	String passwordHash=passwordEncoder.encode(req.getPassword());
    	User user =new User(req.getUsername(),passwordHash,req.getFullname(),req.getEmail());
    	user = userRep.save(user);
    	 return new UserInfoDTO(user);
    } 
    
    
    
    public Optional<UserInfoDTO> findByUsername(String username) {
        return userRep.findByUsername(username)
                .map(user -> new UserInfoDTO(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRep.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) 
                .build();
    }


}
