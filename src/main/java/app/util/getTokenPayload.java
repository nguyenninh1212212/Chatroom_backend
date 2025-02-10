package app.util;

import org.springframework.beans.factory.annotation.Autowired;

import app.repository.UserReponsitory;
import app.service.JWTService;

public class getTokenPayload {
	@Autowired
	private UserReponsitory userRep;
	@Autowired
	private JWTService jwtSer;
	public String UserName(String token) {
		String jwt=token.substring(7);
		String username=jwtSer.extractUserName(jwt);
		return username;
	}
	
}
