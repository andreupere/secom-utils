package cat.uib.secom.utils.strings;

import java.io.UnsupportedEncodingException;

import org.spongycastle.util.encoders.Base64;



public class StringUtils {
	
	/**
	 * @deprecated
	 * */
	public static final String convertByteArrayToString(byte[] in) {
		String s = new String(in);
		return s;
	}
	
	public static final byte[] concatenate(byte[] src1,int offset1,int length1,byte[] src2,int offset2,int length2) {
		byte[] dst = new byte[length1+length2];
		System.arraycopy(src1,offset1,dst,0,length1);
		System.arraycopy(src2,offset2,dst,length1,length2);
		return dst;
	}
	public static final byte[] concatenate(byte[] src1,byte[] src2) {
		return src1 == null ? src2 : (src2 == null ? src1 : concatenate(src1,0,src1.length,src2,0,src2.length));
	}

	
	public static final byte[] concatenate(byte[]... sources) {
		int size = 0;
		for ( byte[] b : sources) {
			size += b.length;
		}
		byte[] output = new byte[size];
		
		int destPos = 0;
		for ( byte[] b : sources) {
			System.arraycopy(b, 0, output, destPos, b.length);
			destPos = destPos + b.length;
		}
		return output;
	}
	
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static final String readHexString(byte[] byteIn) {
		if (byteIn == null) {
			return "";
		}
		String hash = "";
		for(byte aux : byteIn) {
            int b = aux & 0xff; 
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
		return hash;
	}
	
	
	public static String binaryDataToString(byte[] binaryData) {
		return new String( Base64.encode(binaryData) );
	}
	
	public static byte[] StringToBinaryData(String binaryDataEncoded) throws UnsupportedEncodingException {
		return Base64.decode( binaryDataEncoded.getBytes("UTF-8") );
	}
	
	
	
	public static final String cut(Object e) {
		String s = convert(e);
		return s.substring(0, 10);
	}
	
	
	public static final String cut(Object e, int numberChars) {
		String s = convert(e);
		return s.substring(0, numberChars);
	}
	
	private static final String convert(Object e) {
		return e.toString();
	}
	
	
	/**IMPORTANT*/
	public static String encodeBase64(byte[] data) {
		return new String( Base64.encode(data) );
	}
	
	/**IMPORTANT*/
	public static byte[] decodeBase64(String data) {
		return Base64.decode(data);
	}
	
}
