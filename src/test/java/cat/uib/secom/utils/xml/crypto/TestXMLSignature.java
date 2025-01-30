package cat.uib.secom.utils.xml.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cat.uib.secom.utils.xml.XMLUtils;

import junit.framework.TestCase;


public class TestXMLSignature {

	private String xml = "<?xml version=\"1.0\"?><test><element1>hola</element1><element2>qtal</element2></test>";
	
	public Document testXMLSignature() {
		Document signed = null;
		
		try {
			
			
			Document documentXML = XMLUtils.createDocument(xml);
			
			System.out.println(documentXML.getElementsByTagName("element1").item(0).getChildNodes().item(0).getNodeValue());
			
			signed = XMLSignatureUtils.signXml(documentXML, 
					new FileInputStream("/home/apaspai/developing/keys_certs/forRSABenchmark.bks"),
					"pere",
					"rsa1024",
					"pere");
			System.out.println(signed.getElementsByTagName("SignatureValue").item(0).getChildNodes().item(0).getNodeValue());
			
			System.out.println(XMLUtils.toString(documentXML));
			
			
			
			boolean b = XMLSignatureUtils.verifyXml(documentXML);
					
			System.out.println("verification result: " + b);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return signed;
		
	}
	
	public void failingtestRemoveElementContents() {
		
		try {
			
			Document doc = XMLUtils.createDocument(xml);
			System.out.println("\n--------------- BEFORE ----------------------");
			System.out.println( XMLUtils.toString(doc) );
			System.out.println("---------------------------------------------\n");
			
			Node e = doc.getElementsByTagName("element1").item(0);
			e.setTextContent("");
			
			
			System.out.println("\n--------------- REMOVING ELEMENT CONTENTS ----------------------");
			System.out.println( XMLUtils.toString(doc) );
			System.out.println("----------------------------------------------------------------\n");
			
			
			e.setTextContent("qtaaaaaaaaaaaaaaaaaaaaaaaaal");
			
			System.out.println("\n--------------- ADDING ELEMENT CONTENTS ----------------------");
			System.out.println( XMLUtils.toString(doc) );
			System.out.println("----------------------------------------------------------------\n");
			
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * test: signing a XML input, remove node, add node and verify signature
	 * */
	public void test() {
		try {
			Document signed = testXMLSignature();
			
			System.out.println("verification after document signature: " + XMLSignatureUtils.verifyXml(signed));
			Assert.assertTrue(XMLSignatureUtils.verifyXml(signed));
			
			
			signed = XMLUtils.removeElement(signed, "element1");
			System.out.println("\n--------------- REMOVING ELEMENT ----------------------");
			System.out.println( XMLUtils.toString( signed ) );
			System.out.println("-------------------------------------------------------\n");
			
			
			System.out.println("verification before adding node: " + XMLSignatureUtils.verifyXml(signed));
			Assert.assertFalse(XMLSignatureUtils.verifyXml(signed));
			
			Node child = signed.createElement("element1");
			child.setTextContent("hola");
			signed.getDocumentElement().insertBefore(child, signed.getElementsByTagName("element2").item(0));
			
			
			System.out.println("\n--------------- ADDING ELEMENT ----------------------");
			System.out.println( XMLUtils.toString(signed) );
			System.out.println("-----------------------------------------------------\n");
			
			
			System.out.println("verification after adding node: " + XMLSignatureUtils.verifyXml(signed));
			Assert.assertTrue(XMLSignatureUtils.verifyXml(signed));
			
		} catch (Exception e) {
			
		}
	}
	
	
	
	
	
	
}
