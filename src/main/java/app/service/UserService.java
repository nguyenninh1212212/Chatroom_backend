package app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
	private UserReponsitory userRep;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    public UserInfoDTO Register(UserDTO req) {
        if (userRep.findByUsernameOrEmail(req.getUsername(), req.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Username or Email already exists!");
        }

        List<String> errors = new ArrayList<>();
        if (req.getUsername().length() < 6) errors.add("Username must be at least 6 characters.");
        if (req.getPassword().length() < 6) errors.add("Password must be at least 6 characters.");

        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join(" ", errors));
        }

        String passwordHash = passwordEncoder.encode(req.getPassword());
        User user =  User.builder().username(req.getUsername()).email(req.getEmail()).password(passwordHash).created(LocalDateTime.now()).build();
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
