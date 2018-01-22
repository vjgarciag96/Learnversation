package util;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class PasswordEncryptation {
	public static String sha256(String plainTextPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(plainTextPassword.getBytes("UTF-8"));
			byte[] hash = digest.digest();

			return DatatypeConverter.printHexBinary(hash);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}					
	   }
	
}
