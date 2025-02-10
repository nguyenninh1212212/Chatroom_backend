package app.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class HashPassword {
	private static final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	public static String hashPassword (String password) {
		return encoder.encode(password);
	}
	
	public static boolean VerifyPassword(String hashPassword,String password) {
		return encoder.matches(password,hashPassword);
	}

}
