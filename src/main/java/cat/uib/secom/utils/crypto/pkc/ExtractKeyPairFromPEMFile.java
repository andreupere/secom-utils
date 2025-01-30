package cat.uib.secom.utils.crypto.pkc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Sequence;
//import org.bouncycastle.asn1.DEREncodable;
//import org.bouncycastle.asn1.DERObject;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.jce.provider.JDKKeyFactory;
import org.spongycastle.jce.provider.X509CertificateObject;
import org.spongycastle.util.io.pem.PemReader;
//import org.bouncycastle.openssl.PEMReader;




public class ExtractKeyPairFromPEMFile {

	//protected String certificatePath;
	protected String privateKeyPath;
	
	private ExtractKeyPairFromPEMFile(String privateKeyPath) {	}
	
	

//	public static KeyPair readKeyPair3(String privateKeyPath, char [] keyPassword) throws IOException {
//		Security.addProvider(new BouncyCastleProvider());
//		
//		File fpk = new File(privateKeyPath);
//        FileReader fileReader = new FileReader(fpk);
//        PemReader r = new PemReader(fileReader, new DefaultPasswordFinder(keyPassword));
//        try {
//        	System.out.println(r.readObject().getClass());
//        	X509CertificateObject x509 = (X509CertificateObject) r.readObject();
//        	System.out.println(x509);
//        	try {
//        		byte[] x509Encoded = x509.getEncoded();
//        		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        		
//        	
//        		
//        		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(x509Encoded);
//        		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//        		
//        		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(x509Encoded);
//        		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//        		
//        		
//        		
//        		
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            return (KeyPair) r.readObject();
//        } catch (IOException ex) {
//            throw new IOException("The private key could not be decrypted", ex);
//        } finally {
//            r.close();
//            fileReader.close();
//        }
//    }
//	
//	
//	public static KeyPair readKeyPair2(String privateKeyPath, char[] keyPassword) throws IOException {
//		Security.addProvider(new BouncyCastleProvider());
//		
//		File fpk = new File(privateKeyPath);
//		FileInputStream fis = new FileInputStream(fpk);
//		ASN1InputStream ans1is = new ASN1InputStream(fis);
//		ASN1Sequence seq = (ASN1Sequence) ans1is.readObject();
//		
//		fis.close();
//		
//		KeyFactory keyFactory;
//		try {
//			PrivateKeyInfo pki = new PrivateKeyInfo(seq);
//			pki.getPrivateKey();
//			keyFactory = KeyFactory.getInstance("RSA");
//			//PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(der.getEncoded());
//			//PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		//RSAPrivateKeyStructure rsa = new RSAPrivateKeyStructure((ASN1Sequence) der.toASN1Object());
//		//System.out.println(rsa.getExponent1());
//		
//        FileReader fileReader = new FileReader(fpk);
//        PEMReader r = new PEMReader(fileReader, new DefaultPasswordFinder(keyPassword));
//        return (KeyPair) r.readObject();
//	}
//	
//	
//	
//	
//	public static KeyPair loadKeyPair(String path, char [] keyPassword)throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//		// Read Public Key.
//		File filePublicKey = new File(path);
//		FileInputStream fis = new FileInputStream(path);
//		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
//		fis.read(encodedPublicKey);
//		fis.close();
//		 
//		/*// Read Private Key.
//		File filePrivateKey = new File(path + "/private.key");
//		fis = new FileInputStream(path + "/private.key");
//		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
//		fis.read(encodedPrivateKey);
//		fis.close();*/
//		 
//		// Generate KeyPair.
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		
//		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPublicKey);
//		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//		
//		
//		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
//		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//		 
//		
//		 
//		return new KeyPair(publicKey, privateKey);
//	}
//	
//	
//	
//	
//	
//	public static KeyPair readKeyPair(String privateKeyPath, char [] keyPassword) throws IOException {
//		Security.addProvider(new BouncyCastleProvider());
//		
//		File fpk = new File(privateKeyPath);
//        FileReader fileReader = new FileReader(fpk);
//        PEMReader r = new PEMReader(fileReader, new DefaultPasswordFinder(keyPassword));
//        try {
//            return (KeyPair) r.readObject();
//        } catch (IOException ex) {
//            throw new IOException("The private key could not be decrypted", ex);
//        } finally {
//            r.close();
//            fileReader.close();
//        }
//    }
//	
//	
//	
//////	public static AsymmetricKeyParameter TransformRSAPrivateKey(AsymmetricAlgorithm privateKey)
//////    {
//////        
//////        org.bouncycastle.crypto.params.RSAKeyParameters parameters = prov.ExportParameters(true);
//////
//////        return new org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters(
//////            new BigInteger(1,parameters.Modulus),
//////            new BigInteger(1,parameters.Exponent),
//////            new BigInteger(1,parameters.D),
//////            new BigInteger(1,parameters.P),
//////            new BigInteger(1,parameters.Q),
//////            new BigInteger(1,parameters.DP),
//////            new BigInteger(1,parameters.DQ),
//////            new BigInteger(1,parameters.InverseQ));
//////    }
//
//	
//	
//	public static void main(String[] args) {
//		String s ="/home/apaspai/developing/keys_certs/upc_key.pem";
//		try {
//			//KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "robot1".toCharArray());
//			KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "robot1".toCharArray());
//			System.out.println("keyPair extracted. Type: " + keyPair.getPublic().getAlgorithm() );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
	
	
	
	
	
	
	
}
