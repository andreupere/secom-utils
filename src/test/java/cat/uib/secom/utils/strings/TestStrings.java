package cat.uib.secom.utils.strings;

import junit.framework.TestCase;

public class TestStrings {
	
	public void test() {
		String s = "<user-charged-ok>\n\t<serial-number>797</serial-number><fare>6.7</fare><message>ok</message></user-charged-ok>";
		byte[] b = StringUtils.hexStringToByteArray(s);
		System.out.println(b);
	}

}
