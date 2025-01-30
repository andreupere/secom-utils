package cat.uib.secom.utils.networking;

import java.io.IOException;
import java.net.Socket;

public class ServerDefaultImpl extends Server {

	public ServerDefaultImpl(Integer port) {
		super(port);
	}
	
	public void listen() throws IOException {
		while (_listening) {
			Socket socket = _ss.accept();
			new ServerThread(socket).run();
		}
	}
	
	public static void main(String[] args) {
		int port = 10000;
		ServerDefaultImpl s = new ServerDefaultImpl(port);
		try {
			System.out.println("Bind to port " + port);
			s.bind();
			System.out.println("Listening...");
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
