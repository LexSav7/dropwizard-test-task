package auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Secrets {

	public static final String LOGIN_USERNAME = "login";
	public static final String LOGIN_PASSWORD = "password";
	public static final byte[] JWT_SECRET_KEY = genSHA256("secretKey");

	public static byte[] genSHA256(String s) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
	}
}
