package cat.uib.secom.utils.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;


public class Hash {
	
	private byte[] out = null;
	private byte[] in;
	private Digest digest;
	
	public Hash(Digest digest, String in) {
		this.digest = digest;
		this.in = in.getBytes();
	}
	public Hash(Digest digest, byte[] in) {
		this.digest = digest;
		this.in = in;
	}
	public Hash(String in) {
		this.digest = new SHA1Digest();
		this.in = in.getBytes();
	}
	public Hash(byte[] in) {
		this.digest = new SHA1Digest();
		this.in = in;
	}
	
	
	
	
	public byte[] generate() {
		//System.out.println("in: " + in);
		//System.out.println("digest: " + digest);
		digest.update(in, 0, in.length);
		//System.out.println("digest: " + digest);
		int digestSize = digest.getDigestSize();
		//System.out.println("digestSize: " + digestSize + " bytes");
        out = new byte[digestSize];
        //System.out.println("out: " + out);
        digest.doFinal(out, 0);
        //System.out.println("out: " + out);
		return out;
	}
	
	public String readHexString() {
		if (out == null) {
			return "";
		}
		String hash = "";
		for(byte aux : this.out) {
            int b = aux & 0xff; 
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
		return hash;
	}
	
	
	/**
	 * @deprecated
	 * */
	public static final String readHexString(byte[] byteIn) {
		if (byteIn == null) {
			return "";
		}
		String hash = "";
		for(byte aux : byteIn) {
            int b = aux & 0xff; 
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
		return hash;
	}
	
	
	
	/**
	 * Calcula el hash d'un BigInteger amb l'algoritme SHA1
	 */
	public static BigInteger getHash(BigInteger message) throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance("SHA1");
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
    	MessageDigest md = MessageDigest.getInstance("SHA1");
    	byte[] digest = md.digest(message.getBytes());
    	BigInteger number = new BigInteger(1,digest);
    	return number;
    }

	

}

