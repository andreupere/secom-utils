package cat.uib.secom.security;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.junit.Test;

public class TestKeyStore {

	public static final String folder = "/home/apaspai/development/data/multicoupon2d/private/";
	public static final String ksFName = "customerRSA.jks";
	public static final String pwd = "1234";
	public static final String keyAlias = "customer1024";
	
	@Test
	public void testKeyStore() {
		KeyStore keyStore;
		try {
			System.out.println("getInstance to KeyStore");
			keyStore = KeyStoreUtils.getInstance(folder + ksFName, pwd, "jks", "SUN");
			
			System.out.println("get RSA private key from KeyStore");
			java.security.PrivateKey rsaPrivKey = KeyStoreUtils.getRSAPrivateKey(keyStore, keyAlias, pwd);
			
			System.out.println(rsaPrivKey.getFormat());
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}
