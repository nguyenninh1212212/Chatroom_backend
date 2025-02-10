package app.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.service.JWTService;
import app.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private JWTService jwtService;
	@Autowired
	ApplicationContext context;
	@Override
	protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain filterChain) throws IOException, ServletException {
		//Bearer //
		String authHeader=req.getHeader("Authorization");
		String token =null;
		String username=null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ") ) {
			token=authHeader.substring(7);
			username=jwtService.extractUserName(token);
		}
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails= context.getBean(UserService.class).loadUserByUsername(username);
			if(jwtService.validateToken(token,userDetails)) {
				UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			};
		}
		filterChain.doFilter(req, res);
		
	}
}
