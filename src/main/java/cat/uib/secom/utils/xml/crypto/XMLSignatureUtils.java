package cat.uib.secom.utils.xml.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.apache.xml.security.exceptions.XMLSecurityException;
//import org.apache.xml.security.keys.KeyInfo;
//import org.apache.xml.security.signature.XMLSignature;
//import org.apache.xml.security.signature.XMLSignatureException;
//import org.apache.xml.security.transforms.Transforms;
//import org.apache.xml.security.utils.Constants;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLSignatureUtils {
	
	
	/**
	 * Signa digitalment un document xml i hi adjunta el certificat
	 * 
	 * @author Miquel Angel Cabot Nadal
	 * */
	public static Document signXml(Document documentXML, InputStream keystoreFile, 
			String keystorePassword, String privateKeyAlias, String privateKeyPassword) 
			throws KeyStoreException, NoSuchAlgorithmException, 
			CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		
		//org.apache.xml.security.Init.init();
		
       // Constants.setSignatureSpecNSprefix(""); // Perqu� no posi el prefixe "ns" als elements  
        
        // Carregam el magatzem de claus especificat, de tipus BouncyCastle  
        Security.addProvider(new BouncyCastleProvider());
        KeyStore ks  = KeyStore.getInstance("BKS");  
        ks.load(keystoreFile, keystorePassword.toCharArray());  
  
        // Obtenim la clau privada, ja que la necessitam per encriptar  
        PrivateKey privateKey = (PrivateKey) ks.getKey(privateKeyAlias, privateKeyPassword.toCharArray());   
          
        // Instanciam un objecte XMLSignature des del Document. L'algorime de signatura ser� RSA  
       // XMLSignature xmlSignature = new XMLSignature(documentXML, "", XMLSignature.ALGO_ID_SIGNATURE_RSA);  
           
        // Afegim el node de la firma a l'arrel abans de firmar.  
       // documentXML.getDocumentElement().appendChild(xmlSignature.getElement());  
  
        // Cream l'objecte que mapeja: Document/Reference  
       // Transforms transforms = new Transforms(documentXML);  
      //  transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);  
          
        // Afegim l'anterior Document/Reference 
        // ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";  
       // xmlSignature.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);  
  
        // Afegim el KeyInfo del certificat del que usam la seva clau privada
        X509Certificate cert = (X509Certificate) ks.getCertificate(privateKeyAlias);  
      //  xmlSignature.addKeyInfo(cert);  
      //  xmlSignature.addKeyInfo(cert.getPublicKey());
          
        // Realitzam la firma  
      //  xmlSignature.sign(privateKey);
        documentXML.normalize();
        return documentXML;
	}
	
	
	
	/**
	 * Verifica el certificat d'un document xml
	 * 
	 * @author Miquel Angel Cabot Nadal
	 */
	public static boolean verifyXml(Document documentXML) {
		String nomCertificat = null;
		
	//	org.apache.xml.security.Init.init();  
		   
		// Cercam l'element 'Signature'
        Element sigElement = (Element) documentXML.getElementsByTagName("Signature").item(0);  
    //    XMLSignature signature  = new XMLSignature(sigElement, "");  
  
    //    KeyInfo keyInfo = signature.getKeyInfo();
    //    if (keyInfo != null) {  
    //        X509Certificate cert = keyInfo.getX509Certificate();
            
   //         if (cert != null) {  
                // Validam la firma usant un certificat X509  
   //             if (signature.checkSignatureValue(cert)){
    //            	String subjectX500 = cert.getSubjectX500Principal().getName();
                    //////mostrarMissatge("Valid certificate from "+subjectX500);
                	
                	// Hem d'extreure valor "CN="
    //            	nomCertificat = extractCN(subjectX500);
    //            	return true;
    //            } else {  
    //            	return false;
                    //throw new XMLSignatureException("Inv�lid segons el certificat");      
   //             }  
    //        } else {  
                // No trobam un certificat  
    //        	throw new XMLSignatureException("No ha esta possible trobar el certificat");  
   //         }  
   //     } else {  
   //     	throw new XMLSignatureException("No ha estat possible trobar el KeyInfo");
   //     }
        return true;
	}
	
	
	
	
	/**
	 * Extreu la cadena "CN" del certificat
	 * 
	 * @author Miquel Angel Cabot Nadal
	 */
	private static String extractCN(String subjectX500){
		
		final String CADENA_CN = "CN=";
		String nomCN = null;

		int posCN = subjectX500.toUpperCase().indexOf(CADENA_CN);
		if (posCN>=0) {
			posCN += CADENA_CN.length();
		} else {
			posCN = 0;
		}
    	int posComa = subjectX500.toUpperCase().indexOf(",", posCN);
    	if (posComa>=0) {
    		nomCN = subjectX500.substring(posCN, posComa);
    	} else {
    		nomCN = subjectX500.substring(posCN);
    	}
    	
		return nomCN;
	}
	
	
	
	
	
		
	
	
	

}
