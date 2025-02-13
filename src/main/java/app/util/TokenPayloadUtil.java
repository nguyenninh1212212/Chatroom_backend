package app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.service.JWTService;

@Component
public class TokenPayloadUtil {

	@Autowired
	private JWTService jwtService;

	public String getUsername(String token) {
		if (token == null || !token.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Invalid token format");
		}
		String jwt = token.substring(7); // Loại bỏ "Bearer "
		return jwtService.extractUserName(jwt);
	}
}
