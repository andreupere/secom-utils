package cat.uib.secom.utils.crypto.pkc;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import junit.framework.Assert;



import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.junit.Test;

public class TestCertUtils {

	@Test
	public void testCertUtils() {
		SecUtils.initializeBCProvider();
		String toBeCertified = "hola";
		Certificate cert = null;
		try {
			cert = SecUtils.createGPKx509("", 365, toBeCertified.getBytes());
			
			String folder = "/home/apaspai/development/data/cert.data";
			SecUtils.setCertX509(cert, folder);
			Certificate retrievedCert = SecUtils.getCertX509(folder);
			
			Assert.assertEquals(cert.getEndDate(), retrievedCert.getEndDate());
			Assert.assertEquals(cert.getSerialNumber(), retrievedCert.getSerialNumber());
			
		} catch (InvalidCipherTextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
