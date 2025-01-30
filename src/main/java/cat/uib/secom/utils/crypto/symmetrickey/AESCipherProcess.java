package cat.uib.secom.utils.crypto.symmetrickey;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.encoders.Base64;

import cat.uib.secom.utils.crypto.pkc.CipherProcess;
import cat.uib.secom.utils.crypto.pkc.ExtractKeyPairFromPEMFile;
import cat.uib.secom.utils.strings.StringUtils;



public class AESCipherProcess {
	
	protected static String ALGORITHM = "AES";
	
	protected static String ALGORITHM_MODE = "AES/CBC/PKCS7Padding";

	protected Cipher cipher;
	
	protected String secretKey;
	
	
	public AESCipherProcess() {}
	
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
		KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM, "BC");
		keyGen.init(length, new SecureRandom() );
		SecretKey secret = keyGen.generateKey();
		//BigInteger b = new BigInteger(secret.getEncoded());
		return secret; 
	}
	
	public static byte[] encrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher =  Cipher.getInstance(ALGORITHM_MODE, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv); 
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(byte[] data, SecretKey secretKey, IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE, "BC");
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
		byte[] decrypted = decrypt(Base64.decode( data.getBytes("UTF-8") ), 
			    secretKey, 
			    iv); 
		return new String( decrypted );
	}
	
	
	
	public static void main(String[] args) {
		String data = "hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola" +
		"hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola" +
		"hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola" +
		"hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola" +
		"hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola" +
		"hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola";
		
		byte[] datab = data.getBytes();
		System.out.println("data length (bits): " + datab.length);
		try {
			SecretKey secret = AESCipherProcess.createSymmetricKey(128);
			IvParameterSpec iv = AESCipherProcess.createFastIv();
			System.out.println("key size (bits): " + secret.getEncoded().length * 8);
			System.out.println("iv size (bits):" + iv.getIV().length * 8);
			System.out.println("key (hex): " + StringUtils.readHexString( secret.getEncoded() ) );
			System.out.println("iv (hex): " + StringUtils.readHexString( iv.getIV() ));
			System.out.println("key (hex) length: " + StringUtils.readHexString( secret.getEncoded() ).length()*16 );
			System.out.println("key (str) length:" + (new String( secret.getEncoded() )).length() * 8 );
			byte[] encrypted = AESCipherProcess.encrypt(datab, secret, iv);
			byte[] decrypted = AESCipherProcess.decrypt(encrypted, secret, iv);
			System.out.println("encrypted length (bits): " + encrypted.length);
			System.out.println("decrypted: " + new String(decrypted));
			
			
			byte[] join = new byte[secret.getEncoded().length + iv.getIV().length];
			System.arraycopy(secret.getEncoded(), 0, join, 0, secret.getEncoded().length);
			System.arraycopy(iv.getIV(), 0, join, secret.getEncoded().length, iv.getIV().length);
			String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
			KeyPair keyPair = null; // ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
			CipherProcess cp = CipherProcess.getInstance("RSA");
			byte[] e = cp.encrypt(keyPair.getPublic(), join);
			String es = StringUtils.readHexString(e);
			
			// network
			
			byte[] ed = StringUtils.hexStringToByteArray(es);
			byte[] d = cp.decrypt(keyPair.getPrivate(), ed);
			
			byte[] k = Arrays.copyOfRange(d, 0, 15);
			byte[] v = Arrays.copyOfRange(d, 16, d.length);
			
			System.out.println("key: " + StringUtils.readHexString(k));
			System.out.println("iv: " + StringUtils.readHexString(v));
			
			
			
			System.out.println("key as string: " + new String( secret.getEncoded(), "ASCII" ));
			
			
			String str = "<symmetricKeyIvXML><iv length=\"16\">-100, -81, -33, 31, 14, -121, -1, 62, -31, 80, -98, 116, -90, 15, -29, -23</iv>" +
"<key length=\"16\">-52, 13, -88, 91, -27, -19, -101, 0, -29, 33, -113, 81, -107, 17, 10, 123</key> </symmetricKeyIvXML>";
			String str2 = "-100, -81, -33, 31, 14, -121, -1, 62, -31, 80, -98, 116, -90, 15, -29, -23" +
			"-52, 13, -88, 91, -27, -19, -101, 0, -29, 33, -113, 81, -107, 17, 10, 123";
			
			System.out.println(str.getBytes().length * 8);
			System.out.println(str2.getBytes().length * 8);
			
			System.out.println(secret.getEncoded() );
			System.out.println( new String(secret.getEncoded()) );
			
			
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	
}
