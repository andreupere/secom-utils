package cat.uib.secom.utils.networking;

import java.io.IOException;
import java.net.Socket;



public class ServerThread extends Thread {

	private Socket _s;
	
	public ServerThread(Socket s) {
		super("ServerThread");
		_s = s;
	}
	
	public void run() {
		System.out.println("run client... " + _s.getLocalPort());
		
		try {
			byte[]  data = NetUtils.read(_s.getInputStream());
			System.out.println(new String(data, "UTF-8"));
			NetUtils.write(_s.getOutputStream(), "q tal?".getBytes("UTF-8"));
			
			data = NetUtils.read(_s.getInputStream());
			System.out.println(new String(data, "UTF-8"));
			NetUtils.write(_s.getOutputStream(), "jo tb, adeu".getBytes("UTF-8"));
			
			NetUtils.closeStreams(_s.getInputStream(), _s.getOutputStream());
			NetUtils.closeSocket(_s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
}
