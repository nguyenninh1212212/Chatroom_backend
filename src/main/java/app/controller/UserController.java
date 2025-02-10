package app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.dto.ErrorResponse.ErrorResponseDTO;
import app.dto.user.AuthDTO;
import app.dto.user.ReqUserDTO;
import app.dto.user.UserDTO;
import app.dto.user.UserInfoDTO;
import app.entity.User;
import app.service.JWTService;
import app.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class UserController {

    @Autowired
    private UserService userSer;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtSer;

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UserDTO req) {
        try {
     
            UserInfoDTO user = userSer.Register(req);
            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully!",
                    "data", user
            ));
			
		} catch (Exception e) {
			ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
             );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO req, HttpServletResponse response) {
    	try {
    		 
    		Authentication aut= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            if(aut.isAuthenticated()){
            	String accessToken = jwtSer.generateAccessToken(req.getUsername());
            	String refreshToken = jwtSer.generateRefreshToken(req.getUsername());
            	Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                Cookie cookie = new Cookie("refreshToken", refreshToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);// Chỉ hoạt động trên HTTPS
                cookie.setPath("/");
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                response.addCookie(cookie);
            	 return ResponseEntity.ok(Map.of("message",tokens ));
            }
            return ResponseEntity.ok(Map.of("message", "Login fail"));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
    	
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken) {
        String username = jwtSer.extractUserName(refreshToken);

        if (username == null || jwtSer.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(403).body(Map.of("error", "Invalid Refresh Token"));
        }

        String newAccessToken = jwtSer.generateAccessToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);

        return ResponseEntity.ok(tokens);
    }
    
    
}
