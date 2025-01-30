package cat.uib.secom.utils.net.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import cat.uib.secom.utils.net.socket.ServerDefaultImpl;

public class TestNetUtils {

	@Ignore
	@Test
	public void testByteInteger() {
		int i = 20;
		byte[] iByteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
		
		// reconstruir
		int iR = ByteBuffer.wrap(iByteArray).order(ByteOrder.LITTLE_ENDIAN).getInt();
		
		Assert.assertEquals(i, iR);
	}
	
	
	@Test
	public void test() {
		Integer port = 10000;
		ServerDefaultImpl server = new ServerDefaultImpl(port);
		try {
			server.bind();
			server.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
