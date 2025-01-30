package cat.uib.secom.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.spongycastle.jce.provider.BouncyCastleProvider;

public class KeyStoreUtils {
	

	
	/**
	 * Get an instance of KeyStoreUtils to read contents
	 * 
	 * @param uriBKS path to the BKS keystore
	 * @param password the password of BKS keystore 
	 * */
	public static KeyStore getInstance(String uriBKS, String password, String keyStoreType, String provider) throws KeyStoreException, 
																   NoSuchProviderException, 
																   NoSuchAlgorithmException, 
																   CertificateException, 
																   IOException {
		
		Security.addProvider(new BouncyCastleProvider());
		
		File file = new File(uriBKS);
	    FileInputStream is = new FileInputStream(file);
	    KeyStore ks = KeyStore.getInstance(keyStoreType, provider);
	    ks.load(is, password.toCharArray());
	    return ks;
	}
	
	/**
	 * Get the key pair identified by alias and protected by password provided
	 * 
	 * @param ks keystore instance
	 * @param alias name of the key pair inside the BKS keystore
	 * @param password as the passphrase to read the BKS keystore
	 * @return the key pair 
	 * */
	public static Key getKey(KeyStore ks, String alias, String password) throws UnrecoverableKeyException, 
										   KeyStoreException, 
										   NoSuchAlgorithmException {
		Key key = ks.getKey(alias, password.toCharArray());
		return key;
	}
	
	/**
	 * Get the certificate of alias public key
	 * 
	 * @param ks keystore instance
	 * @param alias name of the key pair inside the BKS keystore
	 * @return the certificate
	 * */
	public static Certificate getCertificate(KeyStore ks, String alias) throws KeyStoreException {
		Certificate cert = ks.getCertificate(alias);
		return cert;
	}
	
	
	/**
	 * Get the RSA public key identified by alias inside the keystore
	 * 
	 * @param ks keystore instance
	 * @param alias name of the key pair inside the BKS keystore
	 * @return the RSA public key
	 * */
	public static RSAPublicKey getRSAPublicKey(KeyStore ks, String alias) throws KeyStoreException { 
		Certificate cert = getCertificate(ks, alias);
		RSAPublicKey pubKey = (RSAPublicKey)cert.getPublicKey();
		return pubKey;
	}
	
	/**
	 * Get the RSA private key identified by alias inside the keystore protected by password
	 * 
	 * @param ks keystore instance
	 * @param alias name of the key pair inside the BKS keystore
	 * @param password as the passphrase to read the BKS keystore
	 * @return the RSA private key
	 * */
	public static RSAPrivateKey getRSAPrivateKey(KeyStore ks, String alias, String password) throws UnrecoverableKeyException, 
																				KeyStoreException, 
																				NoSuchAlgorithmException {
		Key key = getKey(ks, alias, password);
	    RSAPrivateKey privKey = (RSAPrivateKey)key;
	    return privKey;
	}
	
	
	public static KeyPair getRSAKeyPair(KeyStore ks, String alias, String password) throws UnrecoverableKeyException, 
																						   KeyStoreException, 
																						   NoSuchAlgorithmException {
		
		return new KeyPair( getRSAPublicKey(ks, alias), getRSAPrivateKey(ks, alias, password) );
	}
	
	
}
