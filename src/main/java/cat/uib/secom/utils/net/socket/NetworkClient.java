package cat.uib.secom.utils.net.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import cat.uib.secom.utils.strings.StringUtils;


public class NetworkClient {

	private Socket _socket = null;
	private BufferedWriter out;
	//private PrintWriter out;
	//private BufferedReader in;
	private BufferedReader in;
	private OutputStream os;
	//private InputStream _is;
	
	private String _url;
	private Integer _port;
	
	
	public NetworkClient(String url, Integer port) {
		
		this._port = port;
		this._url = url;
	}
	
	public NetworkClient(Socket s) {
		_socket = s;
		
		try {
			_socket.setSoLinger(true, 4);
			_socket.setKeepAlive(true);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return _socket;
	}
	
	
	public void createSocket() throws UnknownHostException, IOException {
		if (_socket == null)
			_socket = new Socket(_url, _port);
		_socket.setKeepAlive(true);
		_socket.setSoLinger(true, 4);
	}
	
	public Reader prepareInput() throws IOException { 
		//in = new BufferedReader( new InputStreamReader( _socket.getInputStream() ) );
		in = new BufferedReader( new InputStreamReader( _socket.getInputStream() ) );
		return in;
	}
	


	
	public Writer prepareOutput() throws IOException {
		//out = new BufferedWriter( new OutputStreamWriter( _socket.getOutputStream() ) );
		os = _socket.getOutputStream();
		out =  new BufferedWriter( new OutputStreamWriter( os ) );
		return out;
		//_os = _socket.getOutputStream();
	}
	
	public void flush() throws IOException {
		out.flush();
	}


	public Writer getWriter() {
		return out;
	}
	public Reader getReader() {
		return in;
	}
	
	
	public void closeInput() throws IOException {
		in.close();
		//_is.close();
	}
	
	public void closeOutput() throws IOException {
		out.flush();
		out.close();
		//_os.flush();
		//_os.close();
	}
	
	public void closeSocket() throws IOException {
		_socket.close(); 
	}
	
	
	public String read() throws IOException {
		String s = ( new BufferedReader( in ) ).readLine();
		return s;
	}
	
	
	public void write(String s) throws IOException {
		out.write(s + "\r\n"); 
		out.flush();
	}
	
	
	public static void main(String[] args) {
		NetworkClient c = new NetworkClient("localhost", 10000);
		
		try {
			c.createSocket();
			c.prepareInput();
			c.prepareOutput();
			
			c.write("hola"); 
			
			String s = c.read();
			System.out.println(s);
			
			c.write("va b");
			
			s = c.read();
			System.out.println(s);
			
			
			c.closeInput();
			c.closeOutput();
			c.closeSocket();
			System.out.println("received from server: " + s );
		} catch(IOException e) {
			
		}
	}
	
	
}
