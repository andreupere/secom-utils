package cat.uib.secom.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;

public class HashUtils {

	
	private static final String DIGEST_ALGORITHM = "SHA1";
	
	
	
	public static byte[] getHash2(String message) {
		Digest digest = new SHA1Digest();
		digest.update(message.getBytes(), 0, message.getBytes().length);
		int digestSize = digest.getDigestSize();
        byte[] out = new byte[digestSize];
        digest.doFinal(out, 0);
		return out;
	}
	
	
	/**
	 * Calcula el hash d'un BigInteger amb l'algoritme SHA1
	 */
	public static BigInteger getHash(BigInteger message) throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
    	byte[] digest = md.digest(message.toByteArray());
    	BigInteger number = new BigInteger(1,digest);
    	return number;
    }
	
	/**
	 * Calcula el hash "times" vegades recursivament sobre "message"
	 * */
	public static BigInteger getHash(BigInteger message, Integer times) throws NoSuchAlgorithmException {
		int i = 0;
		while (i<times) {
			message = getHash(message);
			i++;
		}
		return message;
	}
	
	/**
	 * Calcula el hash d'un String amb l'algoritme SHA1
	 */
	public static BigInteger getHash(String message) throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
    	byte[] digest = md.digest(message.getBytes());
    	BigInteger number = new BigInteger(1,digest);
    	return number;
    }
	
}
