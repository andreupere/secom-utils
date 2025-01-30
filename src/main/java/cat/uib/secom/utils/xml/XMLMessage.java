package cat.uib.secom.utils.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cat.uib.secom.utils.net.socket.NetworkClient;

@Root
public abstract class XMLMessage {

	protected Serializer serializer;
	protected Reader reader = null;
	protected Writer writer = null;
	
	protected NetworkClient client;
	
	
	
	@Element(required=true)
	private String type;
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	protected XMLMessage(NetworkClient networkClient) {
		serializer = new Persister();
		client = networkClient;
		reader = client.getReader();
		writer = client.getWriter();
	}
	
	protected XMLMessage(String host, Integer port) throws IOException {
		serializer = new Persister();
		client = new NetworkClient(host, port); 
		client.createSocket();
		client.prepareInput();
		client.prepareOutput();
		reader = client.getReader();
		writer = client.getWriter();
	}
	
	protected XMLMessage() {
		serializer = new Persister();
	}
	
	public void send(XMLMessage xml) throws Exception {
		serializer.write(xml, writer); 
	}
	
	public void writeToFile(XMLMessage xml, File file) throws Exception {
		serializer.write(xml, file);
	}
	
	
	
	public XMLMessage receive(Class<? extends XMLMessage> cls) throws Exception {
		return serializer.read(cls, reader);
	}
	
	public XMLMessage readFromFile(Class<? extends XMLMessage> cls, File file) throws Exception {
		return serializer.read(cls, file);
	}
	
	
	public void close() throws IOException {
		client.closeInput(); 
		client.closeOutput();
		client.closeSocket();
	}
	
	
	public Document toDocument() throws NullPointerException, 
										ParserConfigurationException, 
										SAXException, 
										IOException,
										Exception {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		if (this == null) {
			throw new NullPointerException("You must build an XML document before");
		}
		StringWriter sw = new StringWriter();
		serializer.write(this, sw);
		StringReader sr = new StringReader(sw.toString());
		//Document document = docBuilder.parse(new InputSource( sr ));  
		Document document = docBuilder.parse( new ByteArrayInputStream(sw.toString().getBytes("UTF-8")) );  
		return document;
	}
	
	
	

	
}
