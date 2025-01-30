package cat.uib.secom.utils.properties;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadCfg {
	
	private String _fName;
	private Properties _p;
	
	public LoadCfg(Object obj, String fileName) throws FileNotFoundException, IOException {
		_fName = fileName;
		_p = new Properties();
		_p.load(obj.getClass().getResourceAsStream("/"+_fName));
	}
	
	public LoadCfg(InputStream is) throws IOException {
		_p = new Properties();
		_p.load(is);
	}
	
	
	public String read(String property) {
		return _p.getProperty(property);
	}

}
