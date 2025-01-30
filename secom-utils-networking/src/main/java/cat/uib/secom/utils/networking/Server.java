package cat.uib.secom.utils.networking;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Server {

	protected ServerSocket _ss;
	protected boolean _listening = true;
	private Integer _port;

	

	
	public Server(Integer port) {
		_port = port;
		System.out.println("Im the " + getClass().getName());

	}
	
	public void bind() throws IOException {
		_ss = new ServerSocket(_port);
	}
	
	
	/*public void listen() throws IOException {
		while (_listening) {
			Socket socket = _ss.accept();
			new ServerThread(socket).run();
		}
	}*/
	
	public void listen() throws IOException {}
	

	
	public void close() throws IOException {
		_ss.close();
	}
	
	
	
}
