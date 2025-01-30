package cat.uib.secom.utils.strings;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadCfgUtils {
	
	private String _fName;
	private Properties _p;
	
	public LoadCfgUtils(Object obj, String fileName) throws FileNotFoundException, IOException {
		_fName = fileName;
		_p = new Properties();
		_p.load(obj.getClass().getResourceAsStream("/"+_fName));
	}
	
	public LoadCfgUtils(InputStream is) throws IOException {
		_p = new Properties();
		_p.load(is);
	}
	
	
	public String read(String property) {
		return _p.getProperty(property);
	}

}
