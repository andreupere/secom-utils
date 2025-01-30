package cat.uib.secom.utils.crypto.pkc;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.spongycastle.util.encoders.Base64;
import org.junit.Assert;

import cat.uib.secom.utils.strings.StringUtils;



public class TestPKCryptography {
//
//	
//	public void testExtractKeyPair() {
//		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
//		try {
//			KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
//			System.out.println("keyPair extracted. Type: " + keyPair.getPublic().getAlgorithm() );
//		} catch (IOException e) {
//			Assert.fail();
//			e.printStackTrace();
//		}
//	}
//	
//	
//	
//	public void testSignatureProcess() {
//		String message = "holaaa";
//		byte[] messageb = message.getBytes();
//		
//		
//		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
//		try {
//			KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
//			
//			SignatureProcess sp = SignatureProcess.getInstance("SHA256WithRSAEncryption");
//			long init = System.nanoTime();
//			byte[] signature = sp.sign(keyPair, messageb);
//			System.out.println("signature: " + (System.nanoTime() - init) + " (ms)");
//			
//			signature = Base64.encode(signature);
//			String sig = StringUtils.readHexString(signature);
//			signature = StringUtils.hexStringToByteArray(sig);
//			signature = Base64.decode(signature);
//			
//			boolean verification = sp.verify(keyPair, messageb, signature);
//			Assert.assertTrue(verification);
//			
//		} catch (IOException e) {
//			Assert.fail();
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			Assert.fail();
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			Assert.fail();
//			e.printStackTrace();
//		} catch (SignatureException e) {
//			Assert.fail();
//			e.printStackTrace();
//		}
//	}
//	
////	public void testSignatureProcessFromFile() {
////		System.out.println( getClass().getClassLoader().getResourceAsStream("test.xml") );
////		String fName = "test.xml";
////		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
////		try {
////			KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
////			SignatureProcess sp = SignatureProcess.getInstance("SHA256WithRSAEncryption");
////			BufferedWriter bw = new BufferedWriter( new OutputStreamWriter() );
////			sp.sign(keyPair, br);
////			
////			assertTrue(verification);
////			
////		} catch (IOException e) {
////			e.printStackTrace();
////			fail();
////		} catch (NoSuchAlgorithmException e) {
////			fail();
////			e.printStackTrace();
////		} catch (InvalidKeyException e) {
////			fail();
////			e.printStackTrace();
////		} catch (SignatureException e) {
////			fail();
////			e.printStackTrace();
////		}
////	}
//	
//	
//	
//	
//	
//	public void testCipherProcess() {
//		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
//		
//		String message = "holaaaaaaaaaaaaaaaaaaaaaaaaa hoaaaaaaaaaaaaaaaaaaaaa hoalaaaaaaaaaaaaaaaaaaaaaa hoalalalalal hoallllllllllllllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		byte[] mb = message.getBytes();
//		String mhex = StringUtils.readHexString(mb);
//		
//		System.out.println("Message length (bytes): " + mb.length);
//		
//		try {
//			KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
//			System.out.println("key length (bytes): " + ( (RSAPrivateKey)keyPair.getPrivate() ).getPrivateExponent().bitLength() / 8);
//			CipherProcess cp = CipherProcess.getInstance("RSA/ECB/PKCS1Padding");
//			byte[] encrypted = cp.encrypt(keyPair.getPublic(), mb);
//			
//			byte[] decrypted = cp.decrypt(keyPair.getPrivate(), encrypted);
//			
//			Assert.assertTrue(StringUtils.readHexString(decrypted).equals(mhex));
//			Assert.assertTrue(Arrays.toString(mb).equals( Arrays.toString( decrypted ) ));
//			
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchProviderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	public void testCipherProcess2() {
//		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
//		
//		String message = "52ffbbd8286550a8ac5f946b3c2e5e37357d987f9e163872e8126507355b1767744b6ff3aff0eac1ed795555";
//		
//		
//		try {
//			KeyPair keyPair = ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
//			CipherProcess cp = CipherProcess.getInstance("RSA/ECB/PKCS1Padding");
//			String encrypted = cp.encrypt(keyPair.getPublic(), message);
//			
//			String decrypted = cp.decrypt(keyPair.getPrivate(), encrypted);
//			System.out.println("decrypted: " + decrypted + " message: " + message);
//			//assertTrue(decrypted.equals(message));
//			
//			
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//		} catch (NoSuchProviderException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	
}
