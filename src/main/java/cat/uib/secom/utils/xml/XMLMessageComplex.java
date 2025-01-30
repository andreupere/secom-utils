package cat.uib.secom.utils.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import cat.uib.secom.utils.net.socket.NetworkClient;

public abstract class XMLMessageComplex extends XMLMessage {

	protected XMLMessageComplex(NetworkClient networkClient) {
		super(networkClient);
	}
	
	protected XMLMessageComplex(String host, Integer port) throws IOException {
		super(host, port); 
	}
	
	
	
	public void send(XMLMessage xml) throws Exception {
		serializer.write(xml, writer);
		BufferedWriter bw = new BufferedWriter( writer);
		bw.write("\0");
	}
	
	
	public XMLMessage receive(Class<? extends XMLMessage> cls) throws Exception {
		String str = "";
		String all = "";
		BufferedReader br = new BufferedReader( reader );
		
		while ( !( str = br.readLine() ).contains("\0") ) {
			all += str;
			System.out.println("rebut: " + str);
			//fbw.write(s);
		}
		all += str;
		return serializer.read(cls, all);
	}

}
