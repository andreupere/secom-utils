package cat.uib.secom.utils.network.crypto;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.spongycastle.util.encoders.Base64;


import cat.uib.secom.utils.crypto.symmetrickey.AESCipherProcess;
import cat.uib.secom.utils.net.socket.NetworkClient;
import junit.framework.TestCase;


public class TestAESonNetwork {

	
	public void test() {
		
		Byte[] a = new Byte[] {0x0f, 0x0e, 0x0d, 0x0c, 0x0b, 0x0a, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};
		Byte[] b = new Byte[] {0x0f, 0x0e, 0x0d, 0x0c, 0x0b, 0x0a, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};
		System.out.println(a.equals(b)); 
		
		
		String toBeEnc = "hola q tal";
		
		NetworkClient c = new NetworkClient("localhost", 10000);
		try {
			c.createSocket();
			c.prepareInput();
			c.prepareOutput();
			
			
			IvParameterSpec iv = AESCipherProcess.createFastIv();
			SecretKey sk = AESCipherProcess.createSymmetricKey(128);
			
//			byte[] skByte = sk.getEncoded();
//			c.write( new String(skByte) );
//			c.read();
		
			
			byte[] enc = AESCipherProcess.encrypt(toBeEnc.getBytes("UTF-8"), sk, iv);
			
			byte[] encrypted = Base64.encode(enc);
			
			
			System.out.println("byte[] despres de xifrar: " + enc.hashCode());
			System.out.println("byte[] despres de Base64: " + encrypted.hashCode());
			System.out.println("string amb new string(): " + new String(encrypted));
			
			c.write( new String(encrypted) ); // send encryption
			
			
			c.closeInput();
			c.closeOutput();
			c.closeSocket();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
