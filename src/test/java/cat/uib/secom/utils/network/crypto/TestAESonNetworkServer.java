package cat.uib.secom.utils.network.crypto;


import java.io.IOException;
import java.net.Socket;

import javax.crypto.spec.SecretKeySpec;

import org.spongycastle.util.encoders.Base64;


import cat.uib.secom.utils.crypto.symmetrickey.AESCipherProcess;
import cat.uib.secom.utils.net.socket.NetworkClient;
import cat.uib.secom.utils.net.socket.ServerDefaultImpl;
import cat.uib.secom.utils.net.socket.ServerThread;

import junit.framework.TestCase;

public class TestAESonNetworkServer {

	
	public void test() {
		int port = 10000;
		AESServer aes = new AESServer(port);
		try {
			aes.bind();
			aes.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	protected class AESServer extends ServerDefaultImpl {
		
		
		public AESServer(int port) {
			super(port);
		}
		public void listen() throws IOException {
			while (_listening) {
				Socket socket = _ss.accept();
				new AESServerThread(socket).run();
			}
		}
	}
	
	
	
	
	protected class AESServerThread extends ServerThread {
		private Socket s;
		
		public AESServerThread(Socket s) {
			super(s);
			this.s = s;
		}
		
		public void run() {
			NetworkClient nc = new NetworkClient(s);
			try {
				nc.prepareInput();
				nc.prepareOutput();
				
				
				
				
				//String received = nc.read();  // read key
				//SecretKeySpec skeySpec = new SecretKeySpec(received.getBytes(), 0, 16, "AES");
				
				String received = nc.read(); // read encryption
				byte[] encBytes = Base64.decode(received.getBytes()); 
				//byte[] decryptedBytes = AESCipherProcess.decrypt(encBytes, skeySpec, AESCipherProcess.createFastIv());
				
				System.out.println("string despres de rebut: " + received);
				System.out.println("byte[] abans de base64: " + received.getBytes().hashCode() );
				System.out.println("byte[] despres de base64: " + encBytes.hashCode() );
				//System.out.println("string amb new string(): " + new String(encBytes) );
				//System.out.println("string amb new String() despres de desxifrar: " + new String(decryptedBytes) );
				
				
				
				
				nc.closeInput();
				nc.closeOutput();
				nc.closeSocket();
			} catch(Exception e) {
				
			}
			
		}
		
	}
	
	
}
