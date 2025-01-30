package cat.uib.secom.utils.definitions;

import java.io.File;
import java.io.Reader;

import cat.uib.secom.utils.net.socket.NetworkClient;

public interface AbstractMSGInterface {

	
	public String toXML() throws Exception;
	
	public void toXML(File file) throws Exception;
	
	public void toXML(NetworkClient nc) throws Exception;
	
	public Object toObject(String input) throws Exception;
	
	public Object toObject(Reader input) throws Exception;
	
	
}
