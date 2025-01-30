package cat.uib.secom.utils.crypto.pkc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

import cat.uib.secom.utils.strings.StringUtils;


@Deprecated
public class CipherProcess {

	private static Cipher cipher;
	
	private CipherProcess(Cipher cipher) {
		CipherProcess.cipher = cipher;
	}
	
	public static CipherProcess getInstance(String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher = Cipher.getInstance(algorithm, "BC");
		return new CipherProcess(cipher);
	}
	
	public byte[] encrypt(PublicKey publicKey, byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init( Cipher.ENCRYPT_MODE, publicKey );
	    return cipher.doFinal(data);
	}
	
	public String encrypt(PublicKey publicKey, String hexData) throws UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//byte[] b = data.getBytes("UTF-8");
		//byte[] b = Hex.decode(data);
		byte[] b = StringUtils.hexStringToByteArray(hexData);
		cipher.init( Cipher.ENCRYPT_MODE, publicKey);
		byte[] encrypted = cipher.doFinal(b);
		//System.out.println(Arrays.toString(encrypted));
		//System.out.println(StringUtils.readHexString(encrypted));
		return StringUtils.readHexString(encrypted);
	}
	
	
	public byte[] decrypt(PrivateKey privateKey, byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, privateKey );
	    return cipher.doFinal(data);
	}
	
	public String decrypt(PrivateKey privateKey, String hexData) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//byte[] b = Hex.decode(data);
		byte[] b = StringUtils.hexStringToByteArray(hexData);
		cipher.init( Cipher.DECRYPT_MODE, privateKey);
		byte[] decrypted = cipher.doFinal(b);
		//System.out.println(StringUtils.readHexString(decrypted)); 
		return StringUtils.readHexString(decrypted);
	}
	
	
	public static void main(String[] args) {
		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
		
		String message = "holaa";
		byte[] mb = message.getBytes();
		String mhex = StringUtils.readHexString(mb);
		System.out.println("original: " + mhex);
		
		try {
			KeyPair keyPair = null; //ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
			CipherProcess cp = CipherProcess.getInstance("RSA");
			byte[] encrypted = cp.encrypt(keyPair.getPublic(), mb);
			
			byte[] decrypted = cp.decrypt(keyPair.getPrivate(), encrypted);
			
			System.out.println("decrypted: " + StringUtils.readHexString(decrypted));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		//} catch (IOException e) {
		//	e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
	}
		
	
}
