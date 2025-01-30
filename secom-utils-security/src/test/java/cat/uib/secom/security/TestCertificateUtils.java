package cat.uib.secom.security;

import java.io.IOException;

import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.junit.Test;

public class TestCertificateUtils {
	
	@Test
	public void testMakeCertificate() {
		String key = "key to be certified";
		
		try {
			Certificate cert = CertificateUtils.createGPKx509("hol", 365, key.getBytes());
			System.out.println( cert.getEndDate() ) ;
		} catch (InvalidCipherTextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
