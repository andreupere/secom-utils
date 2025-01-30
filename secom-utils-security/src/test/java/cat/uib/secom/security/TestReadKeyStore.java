package cat.uib.secom.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSAPublicKey;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.provider.JDKPKCS12KeyStore;

public class TestReadKeyStore {

	@org.junit.Test
	public void testReadKeyStore() {
		String keyStoreFN = "/home/apaspai/development/data/multicoupon2d/private/customerRSA.bks";
		String certFN = "/home/apaspai/development/data/multicoupon2d/public/customer_cert.data";
		try {
			KeyStore ks = KeyStoreUtils.getInstance(keyStoreFN, "1234", "BKS", "BC");
			Certificate cert = KeyStoreUtils.getCertificate(ks, "certIssuer1024");
			System.out.println(cert);
			
			org.spongycastle.asn1.x509.Certificate cert2 = CertificateUtils.getCertX509(certFN);
			System.out.println(ASN1Dump.dumpAsString(cert2));
			System.out.println(cert2.getSubjectPublicKeyInfo().getPublicKeyData().getString());
			byte[] workingRSAPubKey = cert2.getSubjectPublicKeyInfo().getPublicKeyData().getBytes();
			
			//JDKPKCS12KeyStore ks2 = new JDKPKCS12KeyStore(BouncyCastleProvider.PROVIDER_NAME, PKCSObjectIdentifiers.sha1WithRSAEncryption );
			
			// extract e, n from public key from bouncycastle asn1 object...
			CertificateFactory cf = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
			java.security.cert.X509Certificate  certJava = (X509Certificate) cf.generateCertificate( new ByteArrayInputStream(cert2.getEncoded()) );
			System.out.println( certJava.getPublicKey() );
			
			//SubjectPublicKeyInfo spki = new SubjectPublicKeyInfo( new AlgorithmIdentifier(PKCSObjectIdentifiers.sha1WithRSAEncryption ), cert2.getSubjectPublicKeyInfo().getPublicKeyData().getEncoded() );
			
			PublicKey pubKey =  KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME).generatePublic( new X509EncodedKeySpec( workingRSAPubKey ) );
			System.out.println("modulus: " + ((java.security.interfaces.RSAPublicKey)pubKey).getModulus());
			
			System.out.println("modulus:" + cert.getPublicKey());
			
			
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
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
