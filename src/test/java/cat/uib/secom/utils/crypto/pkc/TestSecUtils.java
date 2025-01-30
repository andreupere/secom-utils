package cat.uib.secom.utils.crypto.pkc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import junit.framework.Assert;

import org.junit.Test;

public class TestSecUtils {

	public static final String fName = "/home/apaspai/developing/java/micropayment-untraceable/micropayment-untraceable-common/bank.bks";
	public static final String alias = "bank1024";
	public static final String pwd = "1234";
	public static final String keyStoreType = "BKS";
	public static final String toSign = "hola qtal";
	
	@Test
	public void testGetKeyStore() {
		
		try {
			SecUtils.initializeBCProvider();
			KeyStore ks = SecUtils.getKeyStore(keyStoreType, fName, pwd);
			
			Assert.assertNotNull(ks);
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetKeyPair() {
		
		try {
			SecUtils.initializeBCProvider();
			KeyPair kp = SecUtils.getKeyPair(keyStoreType, fName, pwd, alias);
			PrivateKey privKey = kp.getPrivate();
			PublicKey pubKey = kp.getPublic();
			
			RSAPrivateKey privKeyRSA = (RSAPrivateKey) privKey;
			RSAPublicKey pubKeyRSA = (RSAPublicKey) pubKey;
			
			Assert.assertNotNull(kp);
			
			
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSignVerifyData() {
		
		try {
			byte[] data = toSign.getBytes("UTF-8");
			KeyPair kp = SecUtils.getKeyPair(keyStoreType, fName, pwd, alias);
			byte[] signData = SecUtils.signData(data, kp.getPrivate(), null);
			
			boolean ver = SecUtils.verifySign(data, signData, kp.getPublic(), null);
			
			Assert.assertTrue(ver);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		
	}
	
}
