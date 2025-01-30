package cat.uib.secom.utils.crypto.pkc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;


import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.CMPCertificate;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.KeyPurposeId;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.V3TBSCertificateGenerator;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.generators.RSAKeyPairGenerator;
import org.spongycastle.crypto.params.RSAKeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;

import org.spongycastle.util.encoders.Base64;

public class SecUtils {

	final public static String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
	final public static String KEY_ALGORITHM = "RSA";
	final public static String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	final public static String SIGN_ALGORITHM = "SHA256WithRSA";
	
	
	public static KeyStore getKeyStore(String keyStoreType, String fName, String pwd) throws KeyStoreException, 
																							 NoSuchProviderException, 
																							 NoSuchAlgorithmException, 
																							 CertificateException, 
																							 IOException {
		File file = new File(fName);
		if (file.exists()) {
		    FileInputStream fis = new FileInputStream(file);
		    KeyStore ks = KeyStore.getInstance(keyStoreType, PROVIDER);
		    ks.load(fis, pwd.toCharArray());
		    fis.close();
		    return ks;
		}
		return null;
	}
	
	public static KeyPair getKeyPair(String keyStoreType, String fName, String pwd, String alias) throws UnrecoverableKeyException, 
																										 KeyStoreException, 
																										 NoSuchAlgorithmException, 
																										 NoSuchProviderException, 
																										 CertificateException, 
																										 IOException {
		KeyStore ks = SecUtils.getKeyStore(keyStoreType, fName, pwd);
		if (ks == null)
			return null;
		return new KeyPair( ks.getCertificate(alias).getPublicKey(), (PrivateKey) ks.getKey(alias, pwd.toCharArray()));
	}
	
	
	public static byte[] signData(byte[] data, PrivateKey privKey, String algorithm) throws NoSuchAlgorithmException, 
																							NoSuchProviderException, 
																							InvalidKeyException, 
																							SignatureException {
		if (algorithm == null) 
			algorithm = SecUtils.SIGN_ALGORITHM;
		Signature sig = Signature.getInstance(algorithm, PROVIDER);
		sig.initSign(privKey);
		sig.update(data);
		return sig.sign();
	}
	
	
	public static boolean verifySign(byte[] data, byte[] signData, PublicKey pubKey, String algorithm) throws NoSuchAlgorithmException, 
																											  NoSuchProviderException, 
																											  InvalidKeyException, 
																											  SignatureException {
		if (algorithm == null)
			algorithm = SecUtils.SIGN_ALGORITHM;
		Signature sig = Signature.getInstance(algorithm, PROVIDER);
		sig.initVerify(pubKey);
		sig.update(data);
		return sig.verify(signData);
	}
	
	
	public static String encodeBase64(byte[] data) {
		return new String( Base64.encode(data) );
	}
	
	public static byte[] decodeBase64(String data) {
		return Base64.decode(data);
	}
	
	/**
	 * Load BC provider if not loaded
	 */
	public static void initializeBCProvider() {
		try {
			if (Security.getProvider(PROVIDER) == null) {
				Security.addProvider(new BouncyCastleProvider());
			}
		} catch (Exception e) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}
	
	
	
	public static Certificate javaCertToASN1Cert(X509Certificate cert) throws CertificateEncodingException {
		KeyPurposeId a = KeyPurposeId.id_kp_codeSigning;
		AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier((ASN1ObjectIdentifier) a.toASN1Primitive());
		X500Name x500Name = new X500Name( cert.getSubjectX500Principal().getName() );
		
		V3TBSCertificateGenerator certGen = new V3TBSCertificateGenerator();
		
	    certGen.setSerialNumber(new ASN1Integer( cert.getSerialNumber() ));
	   	certGen.setIssuer(new X500Name( cert.getIssuerX500Principal().getName() ));
		certGen.setSubject(x500Name);
		certGen.setSignature(sigAlgId);
		
		byte[] gPKDER = null; // (new DERBitString(gpk)).getBytes(); 
		cert.getEncoded();
		SubjectPublicKeyInfo gpkInfo = new SubjectPublicKeyInfo(sigAlgId, gPKDER);
		//certGen.setSubjectPublicKeyInfo( cert.get gpkInfo);
		certGen.setStartDate(new Time( cert.getNotBefore() ));
		certGen.setEndDate( new Time( cert.getNotAfter() ) );
		
    	return null;
    }
	
	
	/**
	 * Generate Certificate X.509
	 * @throws IOException 
	 * @throws InvalidCipherTextException 
	 * */
	public static final Certificate createGPKx509(String gType, int validityDays, byte[] gpk) throws IOException, InvalidCipherTextException {

		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.DAY_OF_YEAR, validityDays);

		KeyPurposeId a = KeyPurposeId.id_kp_codeSigning;
		AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier((ASN1ObjectIdentifier) a.toASN1Primitive());
		
		//AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier(new ASN1ObjectIdentifier("SHA1WithRSAEncryption"));
		
		X500Name x500Name = new X500Name("CN=Group " + gType);

		V3TBSCertificateGenerator certGen = new V3TBSCertificateGenerator();
	    certGen.setSerialNumber(new ASN1Integer(BigInteger.valueOf(System.currentTimeMillis())));
	   	certGen.setIssuer(new X500Name("CN=Group Manager CA"));
		certGen.setSubject(x500Name);
		certGen.setSignature(sigAlgId);
		
		byte[] gPKDER = (new DERBitString(gpk)).getBytes();
		SubjectPublicKeyInfo gpkInfo = new SubjectPublicKeyInfo(sigAlgId, gPKDER);
		certGen.setSubjectPublicKeyInfo(gpkInfo);
		certGen.setStartDate(new Time(new Date(System.currentTimeMillis())));
		certGen.setEndDate(new Time(expiry.getTime()));
		
		System.out.println("Certificate structure generated, creating SHA1 digest");
		SHA1Digest digester = new SHA1Digest();
		TBSCertificate tbsCert = certGen.generateTBSCertificate();

		ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
		DEROutputStream         dOut = new DEROutputStream(bOut);
		dOut.writeObject(tbsCert);
		
		byte[] certBlock = bOut.toByteArray();	
		digester.update(certBlock, 0, certBlock.length);
		byte[] hash = new byte[digester.getDigestSize()];
		digester.doFinal(hash, 0);

		DigestInfo dInfo = new DigestInfo( new AlgorithmIdentifier(X509ObjectIdentifiers.id_SHA1, null), hash);
		byte[] digest = dInfo.getEncoded("DER");
		
		AsymmetricCipherKeyPair pair = generateRSA();
		//RSAKeyParameters publicKey = (RSAKeyParameters) pair.getPublic();
		RSAPrivateCrtKeyParameters privateKey = (RSAPrivateCrtKeyParameters) pair.getPrivate();
		
		AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());
		rsa.init(true, new RSAKeyParameters(true, privateKey.getModulus(), privateKey.getExponent()));
		byte[] signature = rsa.processBlock(digest, 0, digest.length);
		
        ASN1EncodableVector  v = new ASN1EncodableVector();

        v.add(tbsCert);
        v.add(sigAlgId);
        v.add(new DERBitString(signature));
        
        //byte[] derCert = new DERSequence(v).getEncoded();
        
        ASN1Sequence asn1 = new DERSequence(v);
        ASN1Object asnObj = asn1.toASN1Primitive();
        return Certificate.getInstance(asnObj);
	}
	
	  
	private static AsymmetricCipherKeyPair generateRSA() {
		java.security.SecureRandom sr = new java.security.SecureRandom();
		RSAKeyGenerationParameters kPar = new RSAKeyGenerationParameters(BigInteger.valueOf(3), sr, 1024, 80);
		RSAKeyPairGenerator kGen = new RSAKeyPairGenerator();
		kGen.init(kPar);
	
		return kGen.generateKeyPair();
	}
	
	
	public static void storeCertX509(Certificate certX509, String folder) throws IOException, CertificateException {
		FileOutputStream fos = new FileOutputStream(folder, false);
		fos.write( getPEMFromCert(certX509) );
		fos.flush();
		fos.close();
	}
	
	/**
	 * Stores a byte[] encoded Certificate to a file
	 * 
	 * @param certX509 the asn.1 certificate
	 * @param folder the filename where certX509 should be stored
	 * @exception IOException if and I/O error occurs 
	 * */
	public static void setCertX509(Certificate certX509, String folder) throws IOException {
		FileOutputStream fos = new FileOutputStream(folder, false);
		fos.write( certX509.getEncoded() ); 
		fos.flush();
		fos.close();
	}
	
	/**
	 * Reads a Certificate encoded as byte[] from a file
	 * 
	 * @param folder the filename where the certX509 is stored
	 * @return Certificate object containing the certificate representation
	 * @exception IOException if an I/O error occurs
	 * */
	public static Certificate getCertX509(String folder) throws IOException {
		File file = new File(folder);
		FileInputStream fis = new FileInputStream(file);
		byte[] cert = new byte[(int)file.length()];
		fis.read(cert);
		Certificate x509 = Certificate.getInstance(cert);
		return x509;
	}
	  
	
	/**
     * Returns a certificate in PEM-format.
     *
     * @param cert the certificate to convert to PEM
     * @return byte array containing PEM certificate
     * @exception IOException if the stream cannot be read.
     * @exception CertificateException if the stream does not contain a correct certificate.
     */
    public static byte[] getPEMFromCert(Certificate cert) throws CertificateException, IOException {
    	String beginKey = "-----BEGIN CERTIFICATE-----";
        String endKey = "-----END CERTIFICATE-----";
        ByteArrayOutputStream ostr = new ByteArrayOutputStream();
        PrintStream opstr = new PrintStream(ostr);
        
        byte[] certbuf = Base64.encode(cert.getEncoded());
        opstr.println("Subject: " + cert.getSubject());
        opstr.println("Issuer: " + cert.getIssuer());
        opstr.println(beginKey);
        opstr.println(new String(certbuf));
        opstr.println(endKey);
        
        opstr.close();
        byte[] ret = ostr.toByteArray();
        System.out.println("length: " + ret.length);
        return ret;
    }
	
    
    
    /**
     * Reads a certificate in PEM-format from an InputStream. The stream may contain other things,
     * the first certificate in the stream is read.
     *
     * @param certstream the input stream containing the certificate in PEM-format
     * @return Ordered Collection of X509Certificate, first certificate first, or empty Collection
     * @exception IOException if the stream cannot be read.
     * @exception CertificateException if the stream does not contain a correct certificate.
     */
    public static X509Certificate retrieveCertX509(String folder) throws IOException, CertificateException {
        
        String beginKey = "-----BEGIN CERTIFICATE-----";
        String endKey = "-----END CERTIFICATE-----";
        BufferedReader bufRdr = new BufferedReader(new FileReader(folder));
        X509Certificate x509cert = null;
        while (bufRdr.ready()) {
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            PrintStream opstr = new PrintStream(ostr);
            String temp;
            while (((temp = bufRdr.readLine()) != null) && !temp.equals(beginKey)) {
                continue;
            }
            if (temp == null) {
                throw new IOException("Error in " + folder + ", missing " + beginKey +
                    " boundary");
            }
            while (((temp = bufRdr.readLine()) != null) && !temp.equals(endKey)) {
                opstr.print(temp);
            }
            if (temp == null) {
                throw new IOException("Error in " + folder + ", missing " + endKey +
                    " boundary");
            }
            opstr.close();

            byte[] certbuf = Base64.decode(ostr.toByteArray());
            ostr.close();
            // Phweeew, were done, now decode the cert from file back to X509Certificate object
            CertificateFactory cf = SecUtils.getCertificateFactory();
            x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certbuf));
            //            String dn = x509cert.getSubjectDN().toString();

        }

        return x509cert;
    }
    
    
    
    
    
    public static CertificateFactory getCertificateFactory() {
        try {
            return CertificateFactory.getInstance("X.509", PROVIDER);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }
	  
	
	public static void main(String[] args) {
		String fName = "/home/apaspai/developing/java/micropayment-untraceable/micropayment-untraceable-common/bank.bks";
		try {
			SecUtils.getKeyStore("BKS", fName, "1234");
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
	
}
