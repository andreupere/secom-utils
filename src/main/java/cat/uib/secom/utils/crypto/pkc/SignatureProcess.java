package cat.uib.secom.utils.crypto.pkc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureProcess {

	protected static Signature signature;
	
	private SignatureProcess(Signature signature) {
		SignatureProcess.signature = signature;
	}
	
	public static  SignatureProcess getInstance(String algorithm) throws NoSuchAlgorithmException {
		signature = Signature.getInstance(algorithm);
		return new SignatureProcess(signature);
	}
	
	
	public byte[] sign(PrivateKey privateKey, byte[] data) throws SignatureException, InvalidKeyException {
		signature.initSign(privateKey);
        signature.update(data);
        byte [] signatureBytes = signature.sign();
		return signatureBytes;
	}
	
	
	public byte[] sign(KeyPair keyPair, byte[] data) throws SignatureException, InvalidKeyException{
		return this.sign(keyPair.getPrivate(), data); 
	}
	
	
	public boolean verify(PublicKey publicKey, byte[] data, byte[] inputSignature) throws InvalidKeyException, SignatureException {
		signature.initVerify(publicKey);
        signature.update(data);
        if (signature.verify(inputSignature)) {
            return true;
        } else {
            return false;
        }
	}

	public boolean verify(KeyPair keyPair, byte[] data, byte[] inputSignature) throws InvalidKeyException, SignatureException {
        return this.verify(keyPair.getPublic(), data, inputSignature);
		
	}
	
	public byte[] sign(KeyPair keyPair, BufferedReader br) throws InvalidKeyException, SignatureException, IOException {
		String toSign = readBuffer(br);
		return sign(keyPair, toSign.getBytes());
	}
	
	
//	public byte[] sign(KeyPair keyPair, InputStream is) throws IOException, InvalidKeyException, SignatureException {
//		String toSign = readFile(file);
//		return sign(keyPair, toSign.getBytes()); 
//	}
//	
//	public boolean verify(KeyPair keyPair, InputStream is, byte[] inputSignature) throws IOException, InvalidKeyException, SignatureException {
//		String toVerify = readFile(file);
//		return verify(keyPair, toVerify.getBytes(), inputSignature); 
//		
//	}
	
	
	
	protected String readBuffer(BufferedReader br) throws IOException {
		String s;
		String r = "";
		while ( (s = br.readLine()) != null ) {
			r += s;
		}

		return r;
	}
	
	
	
	
	private String readFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader( file ) );
		String s; 
		String r = "";
		while ( (s = reader.readLine()) != null ) {
			r += s;
		}

		return r;
	}
	
	
	public static void main(String[] args) {
		String message = "holaaa";
		byte[] messageb = message.getBytes();
		
		String s ="/home/apaspai/developing/keys_certs/android_pk.pem";
		try {
			KeyPair keyPair = null; // ExtractKeyPairFromPEMFile.readKeyPair(s, "pere".toCharArray());
			System.out.println("keyPair extracted. Type: " + keyPair.getPublic().getAlgorithm() );
			SignatureProcess sp = SignatureProcess.getInstance("SHA256WithRSAEncryption");
			byte[] signature = sp.sign(keyPair, messageb);
			
			boolean verification = sp.verify(keyPair, messageb, signature);
			System.out.println("verification: " + verification);
			
		//} catch (IOException e) {
		//	e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
