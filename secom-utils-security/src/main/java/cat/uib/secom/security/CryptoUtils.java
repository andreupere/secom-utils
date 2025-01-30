package cat.uib.secom.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.encoders.Base64;

public class CryptoUtils {

	
	final public static String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
	final public static String KEY_ALGORITHM = "RSA";
	final public static String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	final public static String SIGN_ALGORITHM = "SHA256WithRSA";
	
	protected static String ALGORITHM_MODE = "AES/CBC/PKCS7Padding";

	protected static String ALGORITHM = "AES";
	
	
	
	/**
	 * Load BC provider if not loaded
	 */
	public static void initializeBCProvider() {
		try {
			if (Security.getProvider(PROVIDER) == null) {
				Security.addProvider(new BouncyCastleProvider());
			}
		} catch (Exception e) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}
	
	
	public static byte[] signData(byte[] data, PrivateKey privKey, String algorithm) throws NoSuchAlgorithmException, 
																							NoSuchProviderException, 
																							InvalidKeyException, 
																							SignatureException {
		if (algorithm == null) 
			algorithm = SIGN_ALGORITHM;
		Signature sig = Signature.getInstance(algorithm, PROVIDER);
		sig.initSign(privKey);
		sig.update(data);
		return sig.sign();
	}
	
	
	public static boolean verifySign(byte[] data, byte[] signData, PublicKey pubKey, String algorithm) throws NoSuchAlgorithmException, 
																											  NoSuchProviderException, 
																											  InvalidKeyException, 
																											  SignatureException {
		if (algorithm == null)
			algorithm = SIGN_ALGORITHM;
		Signature sig = Signature.getInstance(algorithm, PROVIDER);
		sig.initVerify(pubKey);
		sig.update(data);
		return sig.verify(signData);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	/////////////// symmetric crypto methods
	/////////////////////////////////////////////////////////////////
	
	
	
	public static IvParameterSpec createIv() {
		byte[] ivb = SecureRandom.getSeed(16);
		IvParameterSpec iv = new IvParameterSpec(ivb);
		return iv;
	}
	
	public static IvParameterSpec createFastIv() {
		byte[] ivb = new byte[] {0x0f, 0x0e, 0x0d, 0x0c, 0x0b, 0x0a, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};
		return new IvParameterSpec(ivb);
	}
	
	public static SecretKey createSymmetricKey(int length) throws NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM, PROVIDER);
		keyGen.init(length, new SecureRandom() );
		SecretKey secret = keyGen.generateKey();
		//BigInteger b = new BigInteger(secret.getEncoded());
		return secret; 
	}
	
	public static byte[] encrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher =  Cipher.getInstance(ALGORITHM_MODE, PROVIDER);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv); 
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE, PROVIDER);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(data);
	}
	
	
	public static byte[] encrypt(byte[] data, BigInteger secretKey, IvParameterSpec iv) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		SecretKeySpec sks = new SecretKeySpec(secretKey.toByteArray(), "AES");
		return encrypt(data, sks, iv);
	}
	
	public static byte[] decrypt(byte[] data, BigInteger secretKey, IvParameterSpec iv) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		SecretKeySpec sks = new SecretKeySpec(secretKey.toByteArray(), "AES");
		return decrypt(data, sks, iv); 
	}
	
	public static String encrypt(String data, SecretKey secretKey, IvParameterSpec iv) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		byte[] b = data.getBytes("UTF-8");
		byte[] encrypted = encrypt(b, secretKey, iv);
		return  new String( Base64.encode(encrypted) );
		
	}
	
	public static String decrypt(String data, SecretKey secretKey, IvParameterSpec iv) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		byte[] decrypted = decrypt(Base64.decode( data ), 
			    secretKey, 
			    iv); 
		return new String( decrypted );
	}
}
