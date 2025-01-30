package cat.uib.secom.utils.net.socket.v2;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * @deprecated
 * */
public class BufferedReaderConversation extends BufferedReader {

	public BufferedReaderConversation(Reader in) {
		super(in);
	}
	
	public int read(char[] cbuf, int off, int len) {
		
		return 0;
	}

}
