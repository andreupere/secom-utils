package cat.uib.secom.utils.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
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
	
	private KeyStore _ks = null;

	public void getInstance(String uriBKS, String password) throws KeyStoreException, 
																   NoSuchProviderException, 
																   NoSuchAlgorithmException, 
																   CertificateException, 
																   IOException {
		
		Security.addProvider(new BouncyCastleProvider());
		
		File file = new File(uriBKS);
	    FileInputStream is = new FileInputStream(file);
	    _ks = KeyStore.getInstance("BKS", "BC");
	    _ks.load(is, password.toCharArray());
	}
	
	
	public Key getKey(String alias, String password) throws UnrecoverableKeyException, 
										   KeyStoreException, 
										   NoSuchAlgorithmException {
		Key key = _ks.getKey(alias, password.toCharArray());
		return key;
	}
	
	public Certificate getCertificate(String alias) throws KeyStoreException {
		Certificate cert = _ks.getCertificate(alias);
		return cert;
	}
	
	public RSAPublicKey getRSAPublicKey(String alias) throws KeyStoreException { 
		Certificate cert = getCertificate(alias);
		RSAPublicKey pubKey = (RSAPublicKey)cert.getPublicKey();
		return pubKey;
	}
	
	public RSAPrivateKey getRSAPrivateKey(String alias, String password) throws UnrecoverableKeyException, 
																				KeyStoreException, 
																				NoSuchAlgorithmException {
		Key key = getKey(alias, password);
	    RSAPrivateKey privKey = (RSAPrivateKey)key;
	    return privKey;
	}
	
	
	
}
