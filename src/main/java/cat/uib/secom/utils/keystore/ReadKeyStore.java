package cat.uib.secom.utils.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.spongycastle.jce.provider.BouncyCastleProvider;

public class ReadKeyStore {

	public static String FILE = "";
	
	public static void main(String[] args) {
		
		try {
			
			Security.addProvider(new BouncyCastleProvider());
			
			File file = new File("/home/apaspai/developing/java/micropayment-untraceable/micropayment-untraceable-common/bank.bks");
		    FileInputStream is = new FileInputStream(file);
		    KeyStore ks = KeyStore.getInstance("BKS", "BC");
		    String password = "1234";
		    ks.load(is, password.toCharArray());
		    
		    
		    Key key = ks.getKey("bank1024", "1234".toCharArray());
		    Certificate cert = ks.getCertificate("bank1024");
		    RSAPublicKey pubKey = (RSAPublicKey)cert.getPublicKey();
		    RSAPrivateKey privKey = (RSAPrivateKey)key;
		    
		    print("bank1024", pubKey, privKey);
		    
		    
		    key = ks.getKey("bank2048", "1234".toCharArray());
		    cert = ks.getCertificate("bank2048");
		    pubKey = (RSAPublicKey)cert.getPublicKey();
		    privKey = (RSAPrivateKey)key;
		    
		    
		    print("bank2048", pubKey, privKey);
		    
		    
		    key = ks.getKey("bank4096", "1234".toCharArray());
		    cert = ks.getCertificate("bank4096");
		    pubKey = (RSAPublicKey)cert.getPublicKey();
		    privKey = (RSAPrivateKey)key;
		    
		    print("bank4096", pubKey, privKey);
		    
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void print(String alias, RSAPublicKey pubKey, RSAPrivateKey privKey) {
		System.out.println("\n\nALIAS=" + alias + " bits");
	    System.out.println("bit lenght modulus=" + pubKey.getModulus().bitLength());
	    System.out.println("pub.modulus=" + pubKey.getModulus());
	    System.out.println("pub.publicexp=" + pubKey.getPublicExponent() );
	    
	    System.out.println("priv.modulus=" + privKey.getModulus() );
	    System.out.println("priv.privexp=" + privKey.getPrivateExponent());
	}
	
}



