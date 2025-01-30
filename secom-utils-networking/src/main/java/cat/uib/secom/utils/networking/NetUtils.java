package cat.uib.secom.utils.networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NetUtils {
	
	public static Socket getSocket(String addr, Integer port) throws UnknownHostException, IOException {
		Socket s = new Socket(addr, port);
		return s; 
	}

	public static void closeSocket(Socket s) throws IOException {
		s.close();
	}
	
	public static byte[] read(InputStream is) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] len = new byte[4];
		bis.read(len, 0, len.length);
		
		int length = ByteBuffer.wrap(len).order(ByteOrder.LITTLE_ENDIAN).getInt();	
		System.out.println("data to read=" + length);
		byte[] input = new byte[length];
		int l = 0;
		int r = 0;
		while (l<length) {
			r = bis.read(input, l, length-l);
			l=l+r;
		}
		System.out.println("bytes read=" + r);

		return input;
		
	}
	
	public static void write(OutputStream os, byte[] data) throws IOException {
		
		byte[] send = new byte[data.length + 4];
		byte[] len = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(data.length).array();

		System.arraycopy(len, 0, send, 0, len.length);
		System.arraycopy(data, 0, send, 4, data.length);
		System.out.println("data to write=" + data + "\nlength=" + data.length);
		os.write(send, 0, send.length);
		os.flush();
	}
	
	public static void closeStreams(InputStream is, OutputStream os) throws IOException {
		is.close();
		os.close();
	}
	
	public static void closeInputStream(InputStream is) throws IOException {
		is.close();
	}
	
	public static void closeOutputStream(OutputStream os) throws IOException {
		os.close();
	}
	

	
}
