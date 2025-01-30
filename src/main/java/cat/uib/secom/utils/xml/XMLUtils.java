package cat.uib.secom.utils.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils {
	
	
	/**
	 * Conversion of a DOM document to their corresponding String representation
	 * 
	 * @param doc document to be converted to string
	 * @return string representation of the document
	 * 
	 * @throws NullPointerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws Exception
	 */
	public static String toString(Document doc) 
			   throws NullPointerException, 
					  ParserConfigurationException, 
					  SAXException, 
					  IOException, 
					  Exception {

		StringWriter stw = new StringWriter(); 
		Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
		transformer.transform(new DOMSource(doc), new StreamResult(stw)); 
		return stw.toString();
	}


	
	/**
	 * @deprecated see {@link removeElement(Document signedXML, String tagName)}
	 * */
	public static Document removeSignatureElement(Document signedXML) {
		Element sigNode = (Element) signedXML.getElementsByTagName("Signature").item(0);  
		sigNode.getParentNode().removeChild(sigNode);
		signedXML.normalize();
		return signedXML;
	
	}

	
	/**
	 * Remove an element from a DOM document
	 * 
	 * @param doc document to be managed
	 * @param tagName tag to be removed
	 * @return document with the corresponding element removed from the tree
	 */
	public static Document removeElement(Document doc, String tagName) {
		Element e = (Element) doc.getElementsByTagName(tagName).item(0);
		e.getParentNode().removeChild(e);
		doc.normalize();
		return doc;
	}
	
	
	public static Document changeElementContent(Document doc, String elementTagName, String elementContent) {
		Node e = doc.getElementsByTagName(elementTagName).item(0);
		e.setTextContent(elementContent);
		return doc;
	}
	
	/**
	 * Add an element to a DOM document before the element beforeElementTag
	 * 
	 * @param doc document DOM to be modified
	 * @param newElementTag new element's tag
	 * @param newElementContent new element's content
	 * @param beforeElementTag the element before where newElementTag will be put
	 * 
	 * @return the modified document DOM
	 */
	public static Document addElement(Document doc, String newElementTag, String newElementContent, String beforeElementTag) {
		Node child = doc.createElement(newElementTag);
		child.setTextContent(newElementContent);
		doc.getDocumentElement().insertBefore(child, doc.getElementsByTagName(beforeElementTag).item(0));
		doc.normalize();
		return doc;
	}
	

	
	/**
	 * Create a document from a XML string
	 * 
	 * @param xml to be converted to DOM document
	 * @return the DOM XML document
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document createDocument(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
		Document documentXML = dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));	
		documentXML.getDocumentElement().normalize();
		documentXML.normalize();
		return documentXML;
	}
	
}
