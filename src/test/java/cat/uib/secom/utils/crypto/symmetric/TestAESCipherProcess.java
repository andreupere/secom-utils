package cat.uib.secom.utils.crypto.symmetric;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.encoders.Base64;
import org.junit.Assert;

import cat.uib.secom.utils.crypto.RandomGenerator;
import cat.uib.secom.utils.crypto.symmetrickey.AESCipherProcess;
import cat.uib.secom.utils.strings.StringUtils;

import junit.framework.TestCase;


public class TestAESCipherProcess {

	public void testAESEncryption() {
		String data = "<persona><nom>Andreu Pere</nom><edat>28</edat></persona>";
		try {
			
			SecretKey skey = AESCipherProcess.createSymmetricKey(128);						
			
			String enc2 = AESCipherProcess.encrypt(data, skey, AESCipherProcess.createFastIv());
			String dec2 = AESCipherProcess.decrypt(enc2, skey, AESCipherProcess.createFastIv());
			
			Assert.assertEquals(data, dec2);
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
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
		}
	}
	
	public void testSecretKeyFormats() throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		SecretKey skey = AESCipherProcess.createSymmetricKey(128);
		
		String base64 = new String( Base64.encode( skey.getEncoded() ) );
		
		SecretKeySpec sks = new SecretKeySpec( Base64.decode( base64.getBytes("UTF-8") ), "AES" );
		
		Assert.assertEquals(skey, sks);
		
		
	}
	
}
