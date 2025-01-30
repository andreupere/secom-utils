package cat.uib.secom.utils.net.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import cat.uib.secom.utils.net.socket.NetUtils;

public class TestNetUtils2 {

	@Test
	public void test() {
		try {
			Socket socket = NetUtils.getSocket("localhost", 10000);
			
			NetUtils.write(socket.getOutputStream(), ("hola server! kd kafjaj kijfaijf akfjkjfasfjkafj askfjsa kf jsfas jfkajsfjsafjsifjise as" +
					"jkis jfiasjfiasfj iasjfisa fjijfi sdjfoifj aisfj asi sihughauhyiehjfaui fhahfiua fhue fhaufhuif hasjuf hehaf" +
					" iefiaehfufhguaehfaufhyguf hasufh auf haufhujfhgwauehfif huafh as fhusf hf hasuf au fhasufh asuf hasuh suauf hyau auauf" +
					" jufhaejufh juahfuae hfjuahf a jufh ue fugfhusefh uaf hiahfoiefh ouihfoiuaehfo ahfas hfoe hfif hasioufh ouif has iuofhoui ehfaoh fola" +
					" fhaseifhasif ahseouf hfeioh foui hfui sfhoui hia hoiuioaf hfo afhaseif haoi foaif ashoih fouiaeho hoiaf heo fhoi ahfoi afoif" +
					" efhdfioea hfouiafh oui ahf ahfoius fhafh ai hoifah fi ehai fhasof aifh a ifho fahfhaieh fasihfoif haihfaoui fhaos fiohsae").getBytes("UTF-8"));
			
			byte[] data = NetUtils.read(socket.getInputStream());
			System.out.println( new String(data, "UTF-8") );
			
			NetUtils.write(socket.getOutputStream(), "va be".getBytes("UTF-8"));
			data = NetUtils.read(socket.getInputStream());
			System.out.println(new String(data, "UTF-8"));
			
			NetUtils.closeStreams(socket.getInputStream(), socket.getOutputStream());
			NetUtils.closeSocket(socket);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
