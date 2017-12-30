package org.ohespaco.dominio;

import java.math.BigInteger;
import java.security.MessageDigest;
import static java.security.MessageDigest.getInstance;
import java.security.NoSuchAlgorithmException;

public class Hash {
	/**
	 * md5(..) toma una cadena de texto y devuelve un hash md5.
	 *
	 * @param entrada
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String entrada)  {
		MessageDigest m = null;
		try {
			m = getInstance("MD5");
			m.update(entrada.getBytes(), 0, entrada.length());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new BigInteger(1, m.digest()).toString(16);
	}
}
