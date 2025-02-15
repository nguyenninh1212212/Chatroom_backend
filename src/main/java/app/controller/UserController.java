package app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import app.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    private ValidationService validationService;

    @Autowired
    private JWTService jwtSer;

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UserDTO req) {
     
            UserInfoDTO user = userSer.Register(req);
            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully!",
                    "data", user
            ));
			
		
    }

    @GetMapping("/info")
    ResponseEntity<?> userInfo(@RequestParam(required = true) String username) {

        Optional<UserInfoDTO> user = userSer.findByUsername(username);
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully!",
                "data", user
        ));


    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO req, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            if (auth.isAuthenticated()) {
                UUID user_id = validationService.validateUsername(req.getUsername()).get().getId(); ;
                String email = validationService.validateUsername(req.getUsername()).get().getEmail();
                String fullname = validationService.validateUsername(req.getUsername()).get().getFullname() ;
                String accessToken = jwtSer.generateAccessToken(req.getUsername(),user_id,email,fullname);
                String refreshToken = jwtSer.generateRefreshToken(req.getUsername());

                // Đặt cookie chứa token
                Cookie cookie = new Cookie("token", accessToken);
                cookie.setHttpOnly(false);  // Bảo vệ chống XSS
                cookie.setSecure(false);    // Chỉ gửi qua HTTPS
                cookie.setPath("/");       // Có hiệu lực trên toàn bộ website
                cookie.setMaxAge(7 * 24 * 60 * 60); // Hết hạn sau 7 ngày
                response.addCookie(cookie);

                // Trả về token
                return ResponseEntity.ok(Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Login failed"));
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken) {
        String username = jwtSer.extractUserName(refreshToken);

        if (username == null || jwtSer.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(403).body(Map.of("error", "Invalid Refresh Token"));
        }
        UUID user_id=jwtSer.extractUserId(refreshToken);
        String email = jwtSer.extractEmail(refreshToken);
        String fullname = jwtSer.extractFullname(refreshToken);
        String newAccessToken = jwtSer.generateAccessToken(username,user_id,email,fullname);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);

        return ResponseEntity.ok(tokens);
    }
    
//    @GetMapping("/token")
//    public ResponseEntity<?> getToken(@CookieValue(name = "Token", required = false) String token) {
//        if (token == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token found");
//        }
//        return ResponseEntity.ok(Map.of("Token", token));
//    }
//
    
}
