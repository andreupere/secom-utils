package cat.uib.secom.utils.crypto.pkc;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.spongycastle.jce.provider.BouncyCastleProvider;



public class TestRSABenchmark {
	
	

	
	public void testSignVerify() {
		
		KeyStore ks;
		try {
			Security.addProvider(new BouncyCastleProvider());
			ks = KeyStore.getInstance("BKS");
			ks.load(new FileInputStream("/home/apaspai/developing/keys_certs/forRSABenchmark.bks"), "pere".toCharArray());  
			  
	        // Obtenim la clau privada, ja que la necessitam per encriptar  
	        PrivateKey privateKey = (PrivateKey) ks.getKey("rsa1024", "pere".toCharArray());
	        Certificate cert = ks.getCertificate("rsa1024");
	        
	        PublicKey publicKey = cert.getPublicKey();
	        
	        SignatureProcess sp = SignatureProcess.getInstance("SHA1withRSA");
	        String data = "Hello BBS Signature scheme";
	        int iterations = 20;
	        long totalSign = 0;
	        long totalVerif = 0;
	        long init = 0;
	        long end = 0;
	        int i = 0;
	        while (i < iterations) {
	        	////////// 	sign
	        	init = System.nanoTime();
	        	byte[] sig = sp.sign(privateKey, data.getBytes());
	        	end = System.nanoTime();
	        	System.out.println( (end - init) + " (ns)" );
	        	totalSign = totalSign + (end - init);
	        	System.out.println(sig.length);
	        	
	        	//////////  verify
	        	init = System.nanoTime();
	        	sp.verify(publicKey, data.getBytes(), sig);
	        	end = System.nanoTime();
	        	System.out.println( (end - init) + " (ns)" );
	        	totalVerif = totalVerif + (end - init);
	        	i++;
	        }
	        
	        System.out.println("Sign: " + (totalSign/iterations) + " (ns)");
	        System.out.println("Verify: " + (totalVerif/iterations) + " (ns)");
	        
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
		
		
	}
	
}
