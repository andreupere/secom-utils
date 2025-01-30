package cat.uib.secom.utils.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.Serializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cat.uib.secom.utils.xml.crypto.XMLSignatureUtils;

public class TestXML  {
	
	public TestXML() {	}
	
	
	
	public void test() {
		Message1 m1 = new Message1();
		m1.setType("test");
		m1.setText("contingut test");
		try {
			Serializer s = new Persister();
			StringWriter sw = new StringWriter();
			
			s.write(m1, sw);
			
			System.out.println(sw);
			
			Message1 m1r = s.read(Message1.class, sw.toString());
			System.out.println("Text: " + m1r.getText());
			
			
			Document doc = m1.toDocument();
			System.out.println(doc);
			doc = XMLSignatureUtils.signXml(doc, getClass().getResourceAsStream("/lbsuser.bks"), "1234", "lbsuser", "1234");
			
	        System.out.println( XMLUtils.toString(doc) );
	        
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	

}
